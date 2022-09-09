package com.wolves.mainproject.exception.user;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserAlreadyValidException extends CustomException {

    public UserAlreadyValidException() {
        super(ErrorCode.USER_ALREADY_VALID);
    }
}
