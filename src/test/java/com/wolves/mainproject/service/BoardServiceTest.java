package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.board.comment.BoardComment;
import com.wolves.mainproject.domain.board.comment.BoardCommentRepository;
import com.wolves.mainproject.domain.board.content.BoardContent;
import com.wolves.mainproject.domain.board.content.BoardContentRepository;
import com.wolves.mainproject.domain.board.like.BoardLike;
import com.wolves.mainproject.domain.board.like.BoardLikeRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.dto.request.board.BoardRequestDto;
import com.wolves.mainproject.dto.response.BoardResponseDto;
import com.wolves.mainproject.dto.response.GetBoardResponseDto;
import com.wolves.mainproject.exception.board.BoardPageNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;



//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardContentRepository boardContentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardCommentRepository boardCommentRepository;

    @Autowired
    private BoardLikeRepository boardLikeRepository;


    @BeforeEach
    void createBoard() {
        User user = userRepository.findById(1L).orElseThrow();
        Board board = Board.builder().title("board").user(user).build();
        boardRepository.save(board);
        BoardContent boardContent = BoardContent.builder().board(board).content("boardContent").build();
        boardContentRepository.save(boardContent);
        BoardComment boardComment = BoardComment.builder().board(board).status(true).user(user).refer(null).content("testComment").build();
        boardCommentRepository.save(boardComment);
        BoardComment boardReComment = BoardComment.builder().board(board).status(true).user(user).refer(boardComment).content("testReComment").build();
        boardCommentRepository.save(boardReComment);
    }

    @BeforeEach
    void createBoard2() {
        User user = userRepository.findById(1L).orElseThrow();
        Board board = Board.builder().title("board2").user(user).build();
        boardRepository.save(board);
        BoardContent boardContent = BoardContent.builder().board(board).content("boardContent2").build();
        boardContentRepository.save(boardContent);

    }


    @Test
    void getAllBoard(){
        //when
        List<Board> boards = boardRepository.findAll();
        //then
        assertEquals(2,boards.size());
    }

    @Test
    void searchBoard() {
        //given
        String search = "2";
        //when
        List<Board> boardsList = boardRepository.findByTitleContainingOrUserNicknameContaining(search);
        assert boardsList != null;
        //then
        assertEquals(1, boardsList.size());
    }

    @Test
    void getBoardDetails() {
        //given
        long boardId = 1L;
        //when
        Board board = boardRepository.findById(boardId).orElseThrow(BoardPageNotFoundException::new);
        BoardContent boardContent = boardContentRepository.findById(boardId).orElseThrow(BoardPageNotFoundException::new);
        List<BoardComment> boardCommentList = boardCommentRepository.findByBoard(board);
        List<GetBoardResponseDto> getBoardResponseDtos = boardCommentList.stream().map(boardComment -> new GetBoardResponseDto(boardComment)).toList();
        new BoardResponseDto(board,boardContent,getBoardResponseDtos);
        //then
        assertEquals(board.getUser().getId(),boardContent.getBoard().getUser().getId());
        assertEquals(getBoardResponseDtos.get(0).getId(),getBoardResponseDtos.get(1).getReferComment());
    }


    @Test
    void updateBoard() {
        //Given
        User user = userRepository.findById(1L).orElseThrow();
        Board board = boardRepository.findById(1L).orElseThrow(BoardPageNotFoundException::new);
        if (user.equals(board.getUser())) {
            BoardRequestDto dto = BoardRequestDto.builder().title("updateboard").content("updatecontent").build();
            BoardContent boardContent = boardContentRepository.findByBoard(board);
            //when
            board.update(dto);
            Board boardPS = boardRepository.save(board);
            boardContent.update(dto);
            BoardContent boardContentPS = boardContentRepository.save(boardContent);

            //then
            assertEquals("updateboard", boardPS.getTitle());
            assertEquals("updatecontent", boardContentPS.getContent());
        }
    }

    @Test
    void deleteBoard() {
        //given
        long deleteId = 1L;
        Board board = boardRepository.findById(deleteId).orElseThrow(BoardPageNotFoundException::new);
        //when
        if (deleteId==board.getUser().getId())
            boardRepository.delete(board);
        //then
        assertNull(boardRepository.findById(deleteId).orElse(null));
    }
    @Test
    void findLikeBoardList(){
        //given
        User user = userRepository.findById(1L).orElseThrow();
        Board board = boardRepository.findById(1L).orElseThrow();
        boardLikeRepository.save(BoardLike.builder().board(board).user(user).build());
        Board board2 = boardRepository.findById(2L).orElseThrow();
        boardLikeRepository.save(BoardLike.builder().board(board2).user(user).build());
        //when
        List<BoardLike> boardlikes = boardLikeRepository.findByUser(user);
        assert boardlikes != null;
        //then
        assertEquals(2,boardlikes.size());
    }
}
