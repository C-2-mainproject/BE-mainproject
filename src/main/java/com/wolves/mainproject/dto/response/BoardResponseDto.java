package com.wolves.mainproject.dto.response;

import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.comment.BoardComment;
import com.wolves.mainproject.domain.board.content.BoardContent;
import com.wolves.mainproject.domain.board.like.BoardLike;
import com.wolves.mainproject.domain.user.User;
import lombok.*;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BoardResponseDto {

    private boolean isNotice;
    private String title;
    private long likeCount;
    private long commentCount;
    private LocalDateTime createAt;
    private String content;

    private List<GetBoardResponseDto> comments;

    public void fromBoard(Board board) {
        this.isNotice = board.isNotice();
        this.title = board.getTitle();
        this.likeCount = board.getLikeCount();
        this.commentCount = board.getCommentCount();
        this.createAt = board.getCreateAt();
    }

    public void fromBoardContent(BoardContent boardContent) {
        this.content = boardContent.getContent();
    }

    public void fromBoardComment(List<GetBoardResponseDto> boardComment) {
        this.comments = boardComment;
    }


}
