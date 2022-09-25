package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.board.comment.BoardComment;
import com.wolves.mainproject.domain.board.comment.BoardCommentRepository;
import com.wolves.mainproject.domain.board.content.BoardContent;
import com.wolves.mainproject.domain.board.content.BoardContentRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.dto.request.board.BoardRequestDto;
import com.wolves.mainproject.dto.response.GetBoardResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 단위 테스트 (Service와 관련된 애들만 메모리에 띄우면 됨.)
 * BoardRepository => 가짜 객체로 만들 수 있음.
 *
 */


//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BoardServiceTest {

    @Mock
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardContentRepository boardContentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardCommentRepository boardCommentRepository;


    @BeforeEach
    void createBoard() {
        User user = userRepository.findById(1L).orElseThrow();
        Board board = Board.builder().title("board").user(user).build();
        boardRepository.save(board);
        BoardContent boardContent = BoardContent.builder().board(board).content("boardContent").build();
        boardContentRepository.save(boardContent);
    }

//    @BeforeEach
//    void createComment(){
//        User user = userRepository.findById(1L).orElseThrow();
//        Board board = boardRepository.findById(1L).orElseThrow();
//        BoardComment boardComment = BoardComment.builder().board(board).content("test입니다").user(user).build();
//        boardCommentRepository.save(boardComment);
//
//    }


    @Test
    void updateBoard() {
        //Given
        User user = userRepository.findById(1L).orElseThrow();
        Board board = boardRepository.findById(1L).orElseThrow();
        if (user.equals(board.getUser())) {
            BoardRequestDto dto = BoardRequestDto.builder().title("updateboard").content("updatecontent").build();
            BoardContent boardContent = boardContentRepository.findByBoard(board);

        List<BoardComment> boardCommentList = boardCommentRepository.findByBoard(board);
        List<GetBoardResponseDto> getBoardResponseDtos = boardCommentList.stream().map(boardComment -> new GetBoardResponseDto(boardComment)).toList();
            //when
            board.update(dto);
            Board boardPS = boardRepository.save(board);
            boardContent.update(dto);
            BoardContent boardContentPS = boardContentRepository.save(boardContent);

            //then
            assertEquals("updateboard", dto.getTitle());
            assertEquals("updatecontent", dto.getContent());
        }
    }

    @Test
    void deleteBoard() {
        //given
        long deleteId = 1L;
        Board board = boardRepository.findById(deleteId).orElseThrow();
        //when
        boardRepository.delete(board);

        //then
        assertNull(boardRepository.findById(deleteId).orElse(null));
    }

}

