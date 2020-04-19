package com.setser.learningcenter;

import com.setser.learningcenter.db.DBException;
import com.setser.learningcenter.db.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValidator implements ConstraintValidator<Login, String> {
    private final DBService dbService;

    public LoginValidator(DBService dbService) {
        this.dbService = dbService;
    }

    private final Logger logger = LoggerFactory.getLogger(LoginValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return dbService.findUserByMail(value) == null;
        } catch (DBException e) {
            logger.error(e.getMessage());
            return true;
        }
    }
}
