package com.wolves.mainproject.exception.wordStorage;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class WordStorageTitleTooLargeException extends CustomException {

    public WordStorageTitleTooLargeException() {
        super(ErrorCode.WORD_STORAGE_TITLE_TOO_LARGE);
    }
}
