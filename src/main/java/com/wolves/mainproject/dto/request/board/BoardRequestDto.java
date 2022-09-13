package com.wolves.mainproject.dto.request.board;

import com.wolves.mainproject.domain.board.content.BoardContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BoardRequestDto {
    private String title;
    private String content;
}
