package com.wolves.mainproject.dto.response;

import com.wolves.mainproject.domain.board.comment.BoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardCommentResponseDto {
    private long id;
    private String comment;

    public void fromBoardComment(BoardComment boardComment){
        this.id = boardComment.getId();
        this.comment = boardComment.getContent();
    }
}
