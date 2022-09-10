package com.wolves.mainproject.exception.user.advice;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class UserAdviceNotFoundException extends CustomException {

    public UserAdviceNotFoundException() {
        super(ErrorCode.ADVICE_NOT_FOUND);
    }
}
