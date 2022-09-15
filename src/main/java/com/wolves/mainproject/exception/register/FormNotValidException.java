package com.wolves.mainproject.exception.register;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class FormNotValidException extends CustomException {

    public FormNotValidException() {
        super(ErrorCode.FORM_NOT_VALID);
    }
}
