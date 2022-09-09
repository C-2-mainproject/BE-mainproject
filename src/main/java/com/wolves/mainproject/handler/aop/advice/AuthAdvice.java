package com.wolves.mainproject.handler.aop.advice;

import com.wolves.mainproject.handler.aop.type.Validate;
import com.wolves.mainproject.handler.aop.type.ValidateAdmin;
import com.wolves.mainproject.handler.aop.type.ValidatePrincipal;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@Aspect
public class AuthAdvice {
    private final Validate validate;
    private final ValidateAdmin validateAdmin;

    @Pointcut("@annotation(com.wolves.mainproject.handler.aop.annotation.AuthValidation)")
    public void authValidation() {
    }

    @Pointcut("@annotation(com.wolves.mainproject.handler.aop.annotation.AdminValidation)")
    public void adminValidation() {
    }


    @Before("authValidation()")
    public void validateAuthorization(JoinPoint joinPoint) {
        validate.principalDetails(joinPoint, new ValidatePrincipal());
    }

    @Before("adminValidation()")
    public void validateAdminAuthorization(JoinPoint joinPoint) {
        validate.principalDetails(joinPoint, validateAdmin);
    }
}
