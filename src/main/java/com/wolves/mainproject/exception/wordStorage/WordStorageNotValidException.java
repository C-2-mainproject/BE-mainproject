package com.wolves.mainproject.exception.wordStorage;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class WordStorageNotValidException extends CustomException {
    public WordStorageNotValidException() {
        super(ErrorCode.WORD_STORAGE_NOT_VALID);
    }
}
