package com.wolves.mainproject.exception.register;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EmailConflictException extends CustomException {

    public EmailConflictException() {
        super(ErrorCode.EMAIL_CONFLICT);
    }
}
