package com.wolves.mainproject.exception.wordStorage;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class WordStorageNotFoundException extends CustomException {

    public WordStorageNotFoundException() {
        super(ErrorCode.WORD_STORAGE_NOT_FOUND);
    }
}
