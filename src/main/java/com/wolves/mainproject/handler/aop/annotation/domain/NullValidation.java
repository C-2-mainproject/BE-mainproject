package com.wolves.mainproject.handler.aop.annotation.domain;

import com.wolves.mainproject.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NullValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NullValidation {
    String message() default "";

    ErrorCode exception();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
