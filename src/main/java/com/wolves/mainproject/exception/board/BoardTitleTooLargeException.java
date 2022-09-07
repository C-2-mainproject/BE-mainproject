package com.wolves.mainproject.exception.board;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BoardTitleTooLargeException extends CustomException {

    public BoardTitleTooLargeException() {
        super(ErrorCode.BOARD_TITLE_TOO_LARGE);
    }
}
