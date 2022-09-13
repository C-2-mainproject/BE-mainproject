package com.wolves.mainproject.domain.board.comment;

import com.wolves.mainproject.controller.dto.request.BoardCommentRequestDto;
import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.common.Timestamped;
import com.wolves.mainproject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Entity
@Table(name = "board_comment")
public class BoardComment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;

    @JoinColumn(name = "board_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Board board;

    @JoinColumn(name = "refer_id")
    @ManyToOne
    private BoardComment refer;

    private boolean status;

    public void update(BoardCommentRequestDto boardCommentRequestDto) {
        this.content = boardCommentRequestDto.getContent();
    }

    public void delete() {
        this.status = false;
    }
}
