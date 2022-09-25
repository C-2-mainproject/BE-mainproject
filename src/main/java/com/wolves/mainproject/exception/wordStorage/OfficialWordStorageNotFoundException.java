package com.wolves.mainproject.exception.wordStorage;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class OfficialWordStorageNotFoundException extends CustomException {
    public OfficialWordStorageNotFoundException() {
        super(ErrorCode.OFFICIAL_WORD_STORAGE_NOT_FOUND);
    }
}
