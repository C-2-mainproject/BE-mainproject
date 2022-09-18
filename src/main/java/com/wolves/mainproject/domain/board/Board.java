package com.wolves.mainproject.domain.board;

import com.wolves.mainproject.dto.request.board.BoardRequestDto;
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
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "is_notice", columnDefinition="boolean default false")
    private boolean isNotice = false;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false, name = "like_count")
    private long likeCount;

    @Column(nullable = false, name = "comment_count")
    private long commentCount;

    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;

    @Column(nullable = false)
    private boolean isLike = false;


    public void update(BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle();
    }

    public void bringLikeCount(long boardlike){
        this.likeCount = boardlike;
    }
    public void getCommentCount(long commentCount){
        this.commentCount= commentCount;
    }
}
