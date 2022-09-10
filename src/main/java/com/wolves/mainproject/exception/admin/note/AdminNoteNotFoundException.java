package com.wolves.mainproject.exception.admin.note;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class AdminNoteNotFoundException extends CustomException {

    public AdminNoteNotFoundException() {
        super(ErrorCode.ADMINNOTE_NOT_FOUND);
    }
}
