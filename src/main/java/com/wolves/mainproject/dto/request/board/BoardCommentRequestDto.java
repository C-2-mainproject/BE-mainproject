package com.wolves.mainproject.dto.request.board;

import com.wolves.mainproject.exception.ErrorCode;
import com.wolves.mainproject.handler.aop.annotation.domain.LengthValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BoardCommentRequestDto {
    @LengthValidation(length = 255, exception = ErrorCode.BOARD_COMMENT_TOO_LARGE)
    private String content;

}
