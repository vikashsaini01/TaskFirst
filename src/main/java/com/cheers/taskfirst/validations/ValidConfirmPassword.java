package com.cheers.taskfirst.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy=ConfirmPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Documented
public @interface ValidConfirmPassword {
    String message() default "Passwords don't match";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
