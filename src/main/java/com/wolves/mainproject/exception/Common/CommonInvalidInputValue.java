package com.wolves.mainproject.exception.Common;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class CommonInvalidInputValue extends CustomException {

    public CommonInvalidInputValue() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
