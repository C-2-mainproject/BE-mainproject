package com.wolves.mainproject.exception.wordStorage;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class WordStorageNotAcceptableException extends CustomException {
    public WordStorageNotAcceptableException() {
        super(ErrorCode.WORD_STORAGE_NOT_ACCEPTABLE);
    }
}
