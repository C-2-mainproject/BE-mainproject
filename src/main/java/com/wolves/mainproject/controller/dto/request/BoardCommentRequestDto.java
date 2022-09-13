package com.wolves.mainproject.controller.dto.request;

import com.wolves.mainproject.domain.board.comment.BoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BoardCommentRequestDto {
    private String content;

}
