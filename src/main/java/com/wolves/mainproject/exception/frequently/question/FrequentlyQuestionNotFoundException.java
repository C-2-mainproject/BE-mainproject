package com.wolves.mainproject.exception.frequently.question;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;

public class FrequentlyQuestionNotFoundException extends CustomException {

    public FrequentlyQuestionNotFoundException() {
        super(ErrorCode.QUESTION_NOT_FOUND);
    }
}
