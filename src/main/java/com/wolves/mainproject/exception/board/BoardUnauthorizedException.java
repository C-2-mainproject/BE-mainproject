package com.wolves.mainproject.exception.board;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BoardUnauthorizedException extends CustomException {

    public BoardUnauthorizedException() {
        super(ErrorCode.BOARD_UNAUTHORIZED);
    }
}
