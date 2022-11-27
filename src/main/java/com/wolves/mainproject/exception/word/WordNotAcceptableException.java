package com.wolves.mainproject.exception.word;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class WordNotAcceptableException extends CustomException {
    public WordNotAcceptableException() {
        super(ErrorCode.WORD_NOT_ACCEPTABLE);
    }
}
