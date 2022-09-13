package com.wolves.mainproject.domain.board.content;

import com.wolves.mainproject.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardContentRepository extends JpaRepository<BoardContent, Long> {
    BoardContent findByBoard(Board board);
}
