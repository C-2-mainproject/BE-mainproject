package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.board.comment.BoardComment;
import com.wolves.mainproject.domain.board.comment.BoardCommentRepository;
import com.wolves.mainproject.domain.board.content.BoardContent;
import com.wolves.mainproject.domain.board.content.BoardContentRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.dto.request.board.BoardCommentRequestDto;
import com.wolves.mainproject.exception.board.BoardPageNotFoundException;
import com.wolves.mainproject.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoardCommentServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardContentRepository boardContentRepository;

    @Autowired
    private BoardCommentRepository boardCommentRepository;


    @BeforeEach
    void createBoardAndComment() {
        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        Board board = Board.builder().title("board").user(user).build();
        boardRepository.save(board);
        BoardContent boardContent = BoardContent.builder().board(board).content("boardContent").build();
        boardContentRepository.save(boardContent);
        BoardComment boardComment = BoardComment.builder().board(board).status(true).user(user).refer(null).content("testComment").build();
        boardCommentRepository.save(boardComment);
    }


    @Test
    void updateComment(){
        //given
        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        Board board = boardRepository.findById(1L).orElseThrow(BoardPageNotFoundException::new);
        List<BoardComment> boardComment = boardCommentRepository.findByBoard(board);
        BoardComment boardCommentTest = boardComment.get(0);
        BoardCommentRequestDto dto = new BoardCommentRequestDto("amend");
        //when
        if (user.getId()==boardCommentTest.getUser().getId())
            boardCommentTest.update(dto);
        //then
        assertEquals("amend",boardCommentTest.getContent());
    }

    @Test
    void deleteComment(){
        //given
        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        Board board = boardRepository.findById(1L).orElseThrow(BoardPageNotFoundException::new);
        List<BoardComment> boardComment = boardCommentRepository.findByBoard(board);
        BoardComment boardCommentTest = boardComment.get(0);
        //when
        if (user.getId()==boardCommentTest.getUser().getId())
            boardCommentTest.delete();
        //then
        assertEquals(false,boardCommentTest.isStatus());
    }

    @Test
    void replyComment(){
        //given
        User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);
        Board board = boardRepository.findById(1L).orElseThrow(BoardPageNotFoundException::new);
        List<BoardComment> boardComments = boardCommentRepository.findByBoard(board);
        BoardComment boardCommentTest = boardComments.get(0);
        BoardComment boardComment = BoardComment.builder().board(board).status(true).user(user).refer(boardCommentTest).content("recomment").build();
        //when
        boardCommentRepository.save(boardComment);
        //then
        assertEquals("recomment",boardComment.getContent());
        assertEquals(boardCommentTest.getId(),boardComment.getRefer().getId());
    }

}