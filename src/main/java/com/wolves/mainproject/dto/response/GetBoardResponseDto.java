package com.wolves.mainproject.dto.response;

import com.wolves.mainproject.domain.board.comment.BoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetBoardResponseDto {

    private long referComment;
    private long id;
    private String comment;
    private LocalDateTime createAt;

    private boolean isRecomment = false;

    private boolean isStatus;

    public void fromBoardComment(BoardComment boardComment) {

        this.id = boardComment.getId();
        this.comment = boardComment.getContent();
        this.createAt = boardComment.getCreateAt();
        this.isStatus = boardComment.isStatus();
    }
    public void sendrecomment(BoardComment boardComment){


        this.id = boardComment.getId();
        this.comment = boardComment.getContent();
        this.createAt = boardComment.getCreateAt();
        this.referComment=boardComment.getRefer().getId();
        this.isRecomment = true;
        this.isStatus = boardComment.isStatus();

    }
    public GetBoardResponseDto(BoardComment boardComment) {
        if(!(boardComment.getRefer()==null)){
            sendrecomment(boardComment);
        }
            fromBoardComment(boardComment);
    }

}
