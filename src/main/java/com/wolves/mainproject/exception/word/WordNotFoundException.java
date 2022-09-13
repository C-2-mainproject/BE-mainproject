package com.wolves.mainproject.exception.word;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class WordNotFoundException extends CustomException {

    public WordNotFoundException() {
        super(ErrorCode.WORD_NOT_FOUND);
    }
}
