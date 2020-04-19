package com.setser.learningcenter.controllers;

import com.setser.learningcenter.administrator.Administrator;
import com.setser.learningcenter.course.Course;
import com.setser.learningcenter.db.DBException;
import com.setser.learningcenter.db.DBService;
import com.setser.learningcenter.model.User;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {

    private final DBService dbService;

    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    public CourseController(DBService dbService) {
        this.dbService = dbService;
    }

    @ModelAttribute("allCourses")
    public List<Course> populateCourses() {
        try {
            return dbService.findCourses("", true);
        } catch (DBException e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    @RequestMapping({"/", "/courses"})
    public String showCourses() {
        return "courses";
    }

    @RequestMapping(value = {"/", "/courses"}, params = {"query"})
    public String filterCourses(final ModelMap model, final @RequestParam String query) {
        List<Course> courses = new ArrayList<>();
        try {
            courses = dbService.findCourses(query, true);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
        model.replace("allCourses", courses);
        return "courses";
    }

    @RequestMapping(value = "/course/show", params = {"id"})
    public String showCoursePage(final @NotNull HttpServletRequest request, final @RequestParam("id") Long courseId, @NotNull ModelMap model) {
        try {
            Course course = dbService.getCourseById(courseId);
            model.addAttribute("course", course);
            if (request.getUserPrincipal() == null) {
                if (course.getIsDisplayed()) {
                    return "course";
                } else {
                    return "404";
                }
            }
            User user = dbService.findUserByMail(request.getUserPrincipal().getName());
            model.addAttribute("user", user);
            if (!course.getIsDisplayed()) {
                if (!user.getIsAdmin()) {
                    return "404";
                }
                if (!course.getAdministrators().contains(user)) {
                    return "404";
                }
            }
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
        return "course";
    }
}
