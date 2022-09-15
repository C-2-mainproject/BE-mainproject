package com.wolves.mainproject.handler.aop.annotation.domain;

import com.wolves.mainproject.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = EntityValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityValidation {
    String message() default "";

    long length() default 0;

    ErrorCode exception();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
