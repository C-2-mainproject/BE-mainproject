package com.wolves.mainproject.exception.board;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BoardCommentUnauthorizedException extends CustomException {

    public BoardCommentUnauthorizedException() {
        super(ErrorCode.BOARD_COMMENT_UNAUTHORIZED);
    }
}
