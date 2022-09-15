package com.wolves.mainproject.exception.register;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NicknameConflictException extends CustomException {

    public NicknameConflictException() {
        super(ErrorCode.NICKNAME_CONFLICT);
    }
}
