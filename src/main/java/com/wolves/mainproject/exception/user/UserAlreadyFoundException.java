package com.wolves.mainproject.exception.user;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserAlreadyFoundException extends CustomException {

    public UserAlreadyFoundException() {
        super(ErrorCode.USER_ALREADY_FOUND);
    }
}
