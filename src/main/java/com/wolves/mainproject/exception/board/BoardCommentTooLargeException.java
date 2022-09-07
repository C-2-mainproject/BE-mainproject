package com.wolves.mainproject.exception.board;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BoardCommentTooLargeException extends CustomException {

    public BoardCommentTooLargeException() {
        super(ErrorCode.BOARD_COMMENT_TOO_LARGE);
    }
}
