package com.wolves.mainproject.exception.wordStorage;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class WordStorageDescriptionTooLargeException extends CustomException {

    public WordStorageDescriptionTooLargeException() {
        super(ErrorCode.WORD_STORAGE_DESCRIPTION_TOO_LARGE);
    }
}
