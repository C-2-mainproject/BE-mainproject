package com.wolves.mainproject.handler.aop.type;

import com.wolves.mainproject.config.auth.PrincipalDetails;

public interface ValidateType {
    void validate(PrincipalDetails principalDetails);
}
