package com.wolves.mainproject.exception.user;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class UserUnauthorizedException extends CustomException {

    public UserUnauthorizedException() {
        super(ErrorCode.USER_UNAUTHORIZED);
    }
}