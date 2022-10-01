package com.wolves.mainproject.exception.register;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class OAuthNotFoundException extends CustomException {
    public OAuthNotFoundException(ErrorCode errorCode) {
        super(ErrorCode.OAUTH_NOT_FOUND);
    }
}
