package com.wolves.mainproject.handler.aop.annotation.domain;

import javax.xml.bind.ValidationException;

public class ValidException extends ValidationException {
    public ValidException(String message) {
        super(message);
    }

    public ValidException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ValidException(Throwable exception) {
        super(exception);
    }

    public ValidException(String message, Throwable exception) {
        super(message, exception);
    }

    public ValidException(String message, String errorCode, Throwable exception) {
        super(message, errorCode, exception);
    }
}
