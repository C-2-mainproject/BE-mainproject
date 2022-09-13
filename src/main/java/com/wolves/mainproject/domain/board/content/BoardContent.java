package com.wolves.mainproject.domain.board.content;

import com.wolves.mainproject.dto.request.board.BoardRequestDto;
import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.common.Timestamped;
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
@Table(name = "board_content")
public class BoardContent extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Lob
    private String content;

    @JoinColumn(name = "board_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    private Board board;

    public void update(BoardRequestDto boardRequestDto) {
        this.content = boardRequestDto.getContent();
    }
}