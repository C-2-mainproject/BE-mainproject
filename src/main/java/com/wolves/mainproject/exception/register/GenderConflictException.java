package com.wolves.mainproject.exception.register;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class GenderConflictException extends CustomException {

    public GenderConflictException() {
        super(ErrorCode.GENDER_CONFLICT);
    }
}
