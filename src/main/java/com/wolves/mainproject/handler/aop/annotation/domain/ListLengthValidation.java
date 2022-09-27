package com.wolves.mainproject.handler.aop.annotation.domain;

import com.wolves.mainproject.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ListLengthValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListLengthValidation {
    String message() default "";

    long length() default 0;

    ErrorCode exception();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
