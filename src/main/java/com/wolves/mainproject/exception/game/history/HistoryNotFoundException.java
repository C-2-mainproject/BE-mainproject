package com.wolves.mainproject.exception.game.history;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class HistoryNotFoundException extends CustomException {

    public HistoryNotFoundException() {
        super(ErrorCode.HISTORY_NOT_FOUND);
    }
}
