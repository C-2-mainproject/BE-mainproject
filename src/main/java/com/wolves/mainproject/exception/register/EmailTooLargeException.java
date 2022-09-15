package com.wolves.mainproject.exception.register;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EmailTooLargeException extends CustomException {

    public EmailTooLargeException() {
        super(ErrorCode.EMAIL_TOO_LARGE);
    }
}
