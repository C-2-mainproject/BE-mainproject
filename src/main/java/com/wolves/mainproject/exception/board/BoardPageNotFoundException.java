package com.wolves.mainproject.exception.board;

import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BoardPageNotFoundException extends CustomException {

    public BoardPageNotFoundException() {
        super(ErrorCode.BOARD_PAGE_NOT_FOUND);
    }
}
