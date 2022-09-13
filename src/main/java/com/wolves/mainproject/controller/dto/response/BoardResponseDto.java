package com.wolves.mainproject.controller.dto.response;

import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.content.BoardContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardResponseDto {

    private boolean is_notice;
    private String title;
    private int likeCount;
    private int commentCount;

    public BoardResponseDto(Board board) {

    }
}
