package com.setser.learningcenter.controllers;

import com.setser.learningcenter.administrator.Administrator;
import com.setser.learningcenter.course.Course;
import com.setser.learningcenter.db.DBException;
import com.setser.learningcenter.db.DBService;
import com.setser.learningcenter.model.User;
import com.setser.learningcenter.pupil.Pupil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
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

    @RequestMapping(value = "/course/edit", params = {"id", "action=addAdmin"})
    public String addAdmin(final @RequestPart @Email String mail, @NotNull BindingResult bindingResult,
                           final @RequestParam("id") Long courseId, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/course/show?id=" + courseId;
        }
        try {
            Course course = dbService.getCourseById(courseId);
            User user = dbService.findUserByMail(mail);
            if (!user.getIsAdmin()) {
                return "redirect:/course/show?id=" + courseId;
            }
            Administrator administrator = (Administrator)user;
            course.addAdmin(administrator);
            administrator.addCourse(course);
            dbService.updateAdministrator(administrator);
            model.addAttribute("course", course);
            model.addAttribute("user", user);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
        return "course";
    }

    @RequestMapping(value = "/course/create", params = {"adminId"})
    public String createCourse(final @RequestParam Long adminId) {
        try {
            Administrator admin = dbService.getAdministratorById(adminId);
            Course course = new Course();
            course.setIsDisplayed(false);
            course.setDescription("New course");
            course.addAdmin(admin);
            admin.addCourse(course);
            dbService.updateAdministrator(admin);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
        return "redirect:/user/show";
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

    @RequestMapping(value = "/course/edit", params = {"id", "action=editInfo"})
    public String editCourseInfo(final @Valid Course course, @NotNull BindingResult bindingResult,
                                 final @RequestParam("id") Long courseId, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "course";
        }
        try {
            Course dbCourse = dbService.getCourseById(courseId);
            dbCourse.setDescription(course.getDescription());
            dbCourse.setIsDisplayed(course.getIsDisplayed());
            dbService.updateCourse(dbCourse);
        } catch(DBException e) {
            logger.error(e.getMessage());
        }
        return "redirect:/course/show?id=" + courseId;
    }

    @RequestMapping(value = "/course/register", params = {"courseId", "pupilId", "action=register"})
    public String registerPupilToCourse(final Long courseId, final Long pupilId) {
        try {
            Course course = dbService.getCourseById(courseId);
            Pupil pupil = dbService.getPupilById(pupilId);
            course.addPupil(pupil);
            pupil.addCourse(course);
            dbService.updatePupil(pupil);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
        return "redirect:/course/show?id=" + courseId;
    }

    @RequestMapping(value = "/course/edit", params = {"id", "action=removeAdmin", "adminId"})
    public String removeAdmin(final @RequestParam("id") Long courseId, final Long adminId, final @NotNull ModelMap model) {
        try {
            Course dbCourse = dbService.getCourseById(courseId);
            Administrator admin = dbService.getAdministratorById(adminId);
            dbCourse.deleteAdmin(admin);
            admin.deleteCourse(dbCourse);
            dbService.updateAdministrator(admin);
            model.addAttribute("course", dbCourse);
            model.addAttribute("user", admin);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
        return "course";
    }

    @RequestMapping(value = "/course/show", params = {"id"})
    public String showCoursePage(final @NotNull HttpServletRequest request, final @RequestParam("id") Long courseId,
                                 @NotNull ModelMap model) {
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

    @RequestMapping(value="/course/register", params = {"courseId", "pupilId", "action=cancel"})
    public String unregisterPupilFromCourse(final Long courseId, final Long pupilId) {
        try {
            Course course = dbService.getCourseById(courseId);
            Pupil pupil = dbService.getPupilById(pupilId);
            course.deletePupil(pupil);
            pupil.deleteCourse(course);
            dbService.updatePupil(pupil);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
        return "redirect:/course/show?id=" + courseId;
    }
}
