package com.wolves.mainproject.exception.wordStorage;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class WordStorageUnauthorizedException extends CustomException {

    public WordStorageUnauthorizedException() {
        super(ErrorCode.WORD_STORAGE_UNAUTHORIZED);
    }
}
