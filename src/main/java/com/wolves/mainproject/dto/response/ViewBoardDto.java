package com.wolves.mainproject.dto.response;

import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.comment.BoardComment;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ViewBoardDto {
    private long id;
    private boolean isNotice;
    private String title;
    private long likeCount;
    private long commentCount;
    private LocalDateTime createAt;


    public void fromBoard(Board board){
        this.id = board.getId();
        this.isNotice = board.isNotice();
        this.title = board.getTitle();
        this.likeCount = board.getLikeCount();
        this.commentCount = board.getCommentCount();
        this.createAt = board.getCreateAt();

    }

    public ViewBoardDto(Board board) {
        fromBoard(board);
    }
}
