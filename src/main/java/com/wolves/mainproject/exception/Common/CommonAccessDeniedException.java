package com.wolves.mainproject.exception.Common;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class CommonAccessDeniedException extends CustomException {
    public CommonAccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED);
    }
}
