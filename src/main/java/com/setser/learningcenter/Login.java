package com.setser.learningcenter;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= LoginValidator.class)
public @interface Login {
    String message() default "Login is already registered";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
