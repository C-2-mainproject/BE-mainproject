//package com.wolves.mainproject.domain.board.recomment;
//
//import com.wolves.mainproject.domain.board.comment.BoardComment;
//import com.wolves.mainproject.domain.common.Timestamped;
//import com.wolves.mainproject.domain.user.User;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import javax.persistence.*;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@SuperBuilder
//@Getter
//@Entity
//@Table(name = "board_recomment")
//public class BoardRecomment extends Timestamped {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Column(nullable = false)
//    private String content;
//
//    @JoinColumn(name = "user_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @ManyToOne
//    private User user;
//
//    @JoinColumn(name = "comment_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @ManyToOne
//    private BoardComment comment;
//}
