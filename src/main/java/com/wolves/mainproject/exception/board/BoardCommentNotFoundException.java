package com.wolves.mainproject.exception.board;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BoardCommentNotFoundException extends CustomException {

    public BoardCommentNotFoundException() {
        super(ErrorCode.BOARD_COMMENT_NOT_FOUND);
    }
}
