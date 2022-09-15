package com.wolves.mainproject.handler.aop.annotation.domain;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EntityValidator implements ConstraintValidator<EntityValidation, String> {
    private long length;

    @Override
    public void initialize(EntityValidation constraintAnnotation) {
        this.length = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value) && value.length() <= length;
    }
}
