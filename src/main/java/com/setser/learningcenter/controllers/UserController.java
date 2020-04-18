package com.setser.learningcenter.controllers;

import com.setser.learningcenter.config.WebSecurityConfig;
import com.setser.learningcenter.course.Lesson;
import com.setser.learningcenter.db.DBException;
import com.setser.learningcenter.db.DBService;
import com.setser.learningcenter.forms.RegistrationForm;
import com.setser.learningcenter.model.PublicUser;
import com.setser.learningcenter.model.User;
import com.setser.learningcenter.pupil.Pupil;
import com.setser.learningcenter.teacher.Teacher;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final DBService dbService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(DBService dbService) {
        this.dbService = dbService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationPage(RegistrationForm registrationForm) {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@Valid RegistrationForm registrationForm,
                               @NotNull final BindingResult bindingResult) {
        if (bindingResult.hasErrors() || (registrationForm.getIsTeacher() && registrationForm.getCompanyName().isBlank())) {
            return "register";
        } else {
            PublicUser user;
            if (registrationForm.getIsTeacher()) {
                Teacher teacher = new Teacher();
                teacher.setCompanyName(registrationForm.getCompanyName());
                user = teacher;
            } else {
                user = new Pupil();
            }
            user.setFirstName(registrationForm.getFirstName());
            user.setLastName(registrationForm.getLastName());
            user.setPatronymic(registrationForm.getPatronymic());
            user.setMail(registrationForm.getUsername());
            user.setBio(registrationForm.getBio());
            user.setDisplayMail(true);
            user.setDisplayName(true);
            user.setPassHash(WebSecurityConfig.passwordEncoder().encode(registrationForm.getPassword()));
            try {
                if (registrationForm.getIsTeacher()) {
                    assert user instanceof Teacher;
                    dbService.registerTeacher((Teacher)user);
                } else {
                    assert user instanceof Pupil;
                    dbService.registerPupil((Pupil)user);
                }
            } catch (DBException e) {
                logger.error(e.getMessage());
                return "register";
            }
            return "redirect:/register?success";
        }
    }

    @RequestMapping(value = "/teacher/edit")
    public String editTeacher(@Valid Teacher teacher,
                              @NotNull final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "teacher";
        } else {
            try {
                Teacher teacher_db = (Teacher)dbService.findUserByMail(teacher.getMail());
                teacher.setId(teacher_db.getId());
                if (!teacher.getPassword().isBlank()) {
                    teacher.setPassHash(WebSecurityConfig.passwordEncoder().encode(teacher.getPassword()));
                } else {
                    teacher.setPassHash(teacher_db.getPassHash());
                }
                dbService.updateTeacher(teacher);
            } catch (DBException e) {
                logger.error(e.getMessage());
                return "teacher";
            }
            return "redirect:/user/show?success";
        }
    }

    @RequestMapping(value = "/pupil/edit")
    public String editPupil(@Valid Pupil pupil,
                              @NotNull final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pupil";
        } else {
            try {
                Pupil pupil_db = (Pupil)dbService.findUserByMail(pupil.getMail());
                pupil.setId(pupil_db.getId());
                if (!pupil.getPassword().isBlank()) {
                    pupil.setPassHash(WebSecurityConfig.passwordEncoder().encode(pupil.getPassword()));
                } else {
                    pupil.setPassHash(pupil_db.getPassHash());
                }
                dbService.updatePupil(pupil);
            } catch (DBException e) {
                logger.error(e.getMessage());
                return "pupil";
            }
            return "redirect:/user/show?success";
        }
    }

    @RequestMapping(value = "/user/show")
    public String showPersonalPage(@NotNull final HttpServletRequest request, @NotNull final ModelMap model) {
        try {
            if (request.getUserPrincipal() == null) {
                return "login";
            }
            User user = dbService.findUserByMail(request.getUserPrincipal().getName());
            model.addAttribute("user", user);
            if (user.isAdmin()) {
                return "admin";
            } else if (user.getIsTeacher()) {
                return "teacher";
            } else {
                return "pupil";
            }
        } catch (DBException e) {
            logger.error(e.getMessage());
            return "login";
        }
    }

    @RequestMapping(value = "/user/show", params = {"id", "isTeacher"})
    public String showUserPage(@RequestParam final Long id, @RequestParam boolean isTeacher, @NotNull final ModelMap model) {
        try {
            if (isTeacher) {
                Teacher teacher = dbService.getTeacherById(id);
                model.addAttribute("user", teacher);
                return "teacher";
            } else {
                Pupil pupil = dbService.getPupilById(id);
                model.addAttribute("user", pupil);
                return "pupil";
            }
        } catch (DBException e) {
            logger.error(e.getMessage());
            return "courses";
        }
    }

    @RequestMapping(value = "/user/timetable", params = {"id", "isTeacher"})
    public String getTimetableForUser(final @RequestParam("id") Long id,
                                      final @RequestParam("isTeacher") boolean isTeacher, final ModelMap model) {
        try {
            User user = isTeacher ? dbService.getTeacherById(id) : dbService.getPupilById(id);
            List<Lesson> lessons = new ArrayList<>();
            if (isTeacher) {
                lessons = dbService.getTimetableForTeacher(dbService.getTeacherById(id));
            } else if (user.isPupil()) {
                lessons = dbService.getTimetableForPupil(dbService.getPupilById(id));
            }
            model.addAttribute("lessons", lessons);
        } catch (DBException e) {
            logger.error(e.getMessage());
        }
        return "user_timetable :: table";
    }
}
