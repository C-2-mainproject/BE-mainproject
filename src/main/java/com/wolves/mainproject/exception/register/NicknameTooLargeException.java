package com.wolves.mainproject.exception.register;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NicknameTooLargeException extends CustomException {

    public NicknameTooLargeException() {
        super(ErrorCode.NICKNAME_TOO_LARGE);
    }
}
