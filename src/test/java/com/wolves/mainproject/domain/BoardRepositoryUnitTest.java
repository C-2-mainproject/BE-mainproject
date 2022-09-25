package com.wolves.mainproject.domain;

import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoardRepositoryUnitTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;



    @Test
    public void save_Test() {
        //given
        Board board = new Board(1L, false, "title", 0L, 0L, userRepository.findById(1L).orElseThrow(), false);

        //when
        Board boardEntity = boardRepository.save(board);

        //then
        assertEquals("title",boardEntity.getTitle());
    }

}