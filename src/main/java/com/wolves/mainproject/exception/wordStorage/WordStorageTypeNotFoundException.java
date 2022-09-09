package com.wolves.mainproject.exception.wordStorage;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class WordStorageTypeNotFoundException extends CustomException {

    public WordStorageTypeNotFoundException() {
        super(ErrorCode.WORD_STORAGE_TYPE_NOT_FOUND);
    }
}
