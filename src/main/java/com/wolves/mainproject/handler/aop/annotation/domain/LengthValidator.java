package com.wolves.mainproject.handler.aop.annotation.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LengthValidator implements ConstraintValidator<LengthValidation, String> {
    private long length;

    @Override
    public void initialize(LengthValidation constraintAnnotation) {
        this.length = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.length() <= length;
    }
}
