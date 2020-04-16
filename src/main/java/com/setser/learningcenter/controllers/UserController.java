package com.setser.learningcenter.controllers;

import com.setser.learningcenter.config.WebSecurityConfig;
import com.setser.learningcenter.db.DBException;
import com.setser.learningcenter.db.DBService;
import com.setser.learningcenter.forms.RegistrationForm;
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
                               @NotNull final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors() || (registrationForm.getIsTeacher() && registrationForm.getCompanyName().isBlank())) {
            return "register";
        } else {
            if (registrationForm.getIsTeacher()) {
                Teacher teacher = new Teacher();
                teacher.setFirstName(registrationForm.getFirstName());
                teacher.setLastName(registrationForm.getLastName());
                teacher.setPatronymic(registrationForm.getPatronymic());
                teacher.setMail(registrationForm.getUsername());
                teacher.setBio(registrationForm.getBio());
                teacher.setDisplayMail(true);
                teacher.setDisplayName(true);
                teacher.setCompanyName(registrationForm.getCompanyName());
                teacher.setPassHash(WebSecurityConfig.passwordEncoder().encode(registrationForm.getPassword()));
                try {
                    dbService.registerTeacher(teacher);
                } catch (DBException e) {
                    logger.error(e.getMessage());
                    return "register";
                }
            } else {
                Pupil pupil = new Pupil();
                pupil.setFirstName(registrationForm.getFirstName());
                pupil.setLastName(registrationForm.getLastName());
                pupil.setPatronymic(registrationForm.getPatronymic());
                pupil.setBio(registrationForm.getBio());
                pupil.setDisplayMail(true);
                pupil.setDisplayName(true);
                pupil.setMail(registrationForm.getUsername());
                pupil.setPassHash(WebSecurityConfig.passwordEncoder().encode(registrationForm.getPassword()));
                try {
                    dbService.registerPupil(pupil);
                } catch (DBException e) {
                    logger.error(e.getMessage());
                    return "register";
                }
            }
            return "redirect:/register?success";
        }
    }

    @RequestMapping(value = "/user/show")
    public String showUserPage(@NotNull final HttpServletRequest request, @NotNull final ModelMap model) {
        try {
            User user = dbService.findUserByMail(request.getUserPrincipal().getName());
            model.addAttribute("user", user);
            if (user.isAdmin()) {
                return "admin";
            } else if (user.isTeacher()) {
                return "teacher";
            } else {
                return "pupil";
            }
        } catch (DBException e) {
            logger.error(e.getMessage());
            return "login";
        }
    }
}
