package com.wolves.mainproject.handler.aop.type;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.exception.user.UserUnauthorizedException;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class Validate {

    public void principalDetails(JoinPoint joinPoint, ValidateType validateType){
        boolean isValidated = false;
        for(Object arg : joinPoint.getArgs()){
            if (arg instanceof PrincipalDetails principalDetails){
                validateType.validate(principalDetails);
                isValidated = true;
            }
        }

        if (!isValidated)
            throw new UserUnauthorizedException();
    }
}
