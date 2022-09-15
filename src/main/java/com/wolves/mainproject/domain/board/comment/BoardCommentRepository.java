package com.wolves.mainproject.domain.board.comment;

import com.wolves.mainproject.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
    List<BoardComment> findByBoard(Board board);
}
