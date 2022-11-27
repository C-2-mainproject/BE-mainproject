package com.wolves.mainproject.exception.game;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class WordStorageSyncTimeoutException extends CustomException {
    public WordStorageSyncTimeoutException() {
        super(ErrorCode.WORD_STORAGE_SYNC_TIMEOUT);
    }
}
