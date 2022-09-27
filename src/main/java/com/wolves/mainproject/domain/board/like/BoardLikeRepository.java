package com.wolves.mainproject.domain.board.like;

import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {


    boolean existsByUserAndBoard(User user, Board board);

    void deleteByUserAndBoard(User user, Board board);


    List<BoardLike> findByBoard(Board board);

    List<BoardLike> findByUser(User user);
}