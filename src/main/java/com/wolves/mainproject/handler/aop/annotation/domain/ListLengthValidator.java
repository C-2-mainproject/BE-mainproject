package com.wolves.mainproject.handler.aop.annotation.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ListLengthValidator implements ConstraintValidator<ListLengthValidation, List<?>> {
    private long length;

    @Override
    public void initialize(ListLengthValidation constraintAnnotation) {
        this.length = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        return value.size() <= length;
    }
}
