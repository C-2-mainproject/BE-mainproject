package com.wolves.mainproject.dto.request.board;

import com.wolves.mainproject.domain.board.content.BoardContent;
import com.wolves.mainproject.exception.ErrorCode;
import com.wolves.mainproject.handler.aop.annotation.domain.LengthValidation;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BoardRequestDto {
    @LengthValidation(length = 40, exception = ErrorCode.BOARD_TITLE_TOO_LARGE)
    private String title;
    private String content;
}
