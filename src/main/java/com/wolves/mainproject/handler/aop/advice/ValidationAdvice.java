package com.wolves.mainproject.handler.aop.advice;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@Aspect
public class ValidationAdvice {

    @Around("execution(* com.wolves.mainproject.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        validateDto(proceedingJoinPoint.getArgs());
        return proceedingJoinPoint.proceed();
    }

    private void validateDto(Object[] args){
        for(Object arg : args){
            if (arg instanceof BindingResult bindingResult){
                if (bindingResult.hasErrors()){
                    for (int i = 0; i < bindingResult.getFieldError().getArguments().length; i++) {
                        Object validArg = bindingResult.getFieldError().getArguments()[i];
                        if (validArg.getClass().equals(ErrorCode.class))
                            throw new CustomException((ErrorCode) validArg);
                    }
                }
            }
        }
    }
}
