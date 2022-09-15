package com.wolves.mainproject.exception.Common;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class SqlConflictException extends CustomException {

    public SqlConflictException() {
        super(ErrorCode.SQL_CONFLICT);
    }
}
