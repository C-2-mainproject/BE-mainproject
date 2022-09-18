package com.wolves.mainproject.dto.request.board;

import com.amazonaws.services.dynamodbv2.xspec.B;
import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.content.BoardContent;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.type.RoleType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetBoardDto {
    private boolean isNotice;
    private String title;
    private long likeCount;
    private long commentCount;

    private LocalDateTime createAt;
    private String content;

    public void fromBoard(Board board){
        this.isNotice = board.isNotice();
        this.title = board.getTitle();
        this.likeCount = board.getLikeCount();
        this.commentCount = board.getCommentCount();
        this.createAt = board.getCreateAt();
    }
    public void fromBoardContent(BoardContent boardContent){
        this.content = boardContent.getContent();
    }

    public GetBoardDto(User user, Board board, BoardContent boardContent){
        if (user.getRole().equals(RoleType.ROLE_ADMIN)){
            this.isNotice = true;
        }else {this.isNotice = board.isNotice();}
        this.title = board.getTitle();
        this.likeCount = board.getLikeCount();
        this.commentCount = board.getCommentCount();
        this.createAt = board.getCreateAt();
        this.content = boardContent.getContent();

    }

}