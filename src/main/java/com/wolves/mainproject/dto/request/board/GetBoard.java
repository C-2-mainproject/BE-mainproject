package com.wolves.mainproject.dto.request.board;

import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.content.BoardContent;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetBoard {
    private Board board;
    private BoardContent boardContent;
}
