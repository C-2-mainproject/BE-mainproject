package com.wolves.mainproject.controller.dto.request;

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
