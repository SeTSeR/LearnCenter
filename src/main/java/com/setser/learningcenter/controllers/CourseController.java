package com.setser.learningcenter.controllers;

import com.setser.learningcenter.course.Course;
import com.setser.learningcenter.db.DBException;
import com.setser.learningcenter.db.DBService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {

    private final DBService dbService;

    public CourseController(DBService dbService) {
        this.dbService = dbService;
    }

    @ModelAttribute("allCourses")
    public List<Course> populateCourses() {
        try {
            return dbService.findCourses("", true);
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @RequestMapping({"/", "/courses"})
    public String showCourses() {
        return "courses";
    }

    @RequestMapping(value = {"/", "/courses"}, params = {"query"})
    public String filterCourses(final ModelMap model, final @RequestParam("query") String query) {
        List<Course> courses = new ArrayList<>();
        try {
            courses = dbService.findCourses(query, true);
        } catch (DBException e) {
            System.out.println(e.getMessage());
        }
        model.replace("allCourses", courses);
        return "courses";
    }
}
