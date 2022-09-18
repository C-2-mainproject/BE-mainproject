package com.wolves.mainproject.service;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.wolves.mainproject.domain.board.comment.BoardComment;
import com.wolves.mainproject.domain.board.like.BoardLike;
import com.wolves.mainproject.dto.request.board.BoardRequestDto;
import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.board.comment.BoardCommentRepository;
import com.wolves.mainproject.domain.board.content.BoardContent;
import com.wolves.mainproject.domain.board.content.BoardContentRepository;
import com.wolves.mainproject.domain.board.like.BoardLikeRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.dto.request.board.GetBoardDto;
import com.wolves.mainproject.dto.response.*;
import com.wolves.mainproject.exception.board.BoardPageNotFoundException;
import com.wolves.mainproject.exception.board.BoardTitleTooLargeException;
import com.wolves.mainproject.exception.board.BoardUnauthorizedException;
import com.wolves.mainproject.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardContentRepository boardContentRepository;

    private final BoardCommentRepository boardCommentRepository;

    private final BoardLikeRepository boardLikeRepository;


    @Transactional(readOnly = true)
    public List<ViewBoardDto> getBoardAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(board -> new ViewBoardDto(board)).toList();
    }


    @Transactional(readOnly = true)
    public List<ViewBoardDto> searchBoard(String search) {
        List<Board> boards = boardRepository.findByTitleContaining(search);
        return boards.stream().map(board -> new ViewBoardDto(board)).toList();
    }

    @Transactional
    public BoardResponseDto getBoardById(long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(() -> new BoardPageNotFoundException());
        BoardContent boardContent = boardContentRepository.findById(board_id).orElseThrow();
        List<BoardComment> boardComments = boardCommentRepository.findByBoard(board);
        List<GetBoardResponseDto> getBoardResponseDtos = boardComments.stream().map(boardComment -> new GetBoardResponseDto(boardComment)).toList();
        return new BoardResponseDto(board,boardContent,getBoardResponseDtos);
    }

    @Transactional
    public GetBoardDto creatBoard(User user, BoardRequestDto boardRequestDto) {
        if (boardRequestDto.getTitle().length() > 40)
            throw new BoardTitleTooLargeException();
        Board board = Board.builder().title(boardRequestDto.getTitle()).user(user).build();
        boardRepository.save(board);
        BoardContent boardContent = BoardContent.builder().board(board).content(boardRequestDto.getContent()).build();
        boardContentRepository.save(boardContent);
        return new GetBoardDto(user, board, boardContent);
    }

    @Transactional
    public BoardResponseDto updateBoard(User user, long board_id, BoardRequestDto boardRequestDto) {
        if (boardRequestDto.getTitle().length() > 40) {
            throw new BoardTitleTooLargeException();
        }
        Board board = boardRepository.findById(board_id).orElseThrow(() -> new BoardPageNotFoundException());
        if (!user.getUsername().equals(board.getUser().getUsername())) {
            throw new BoardUnauthorizedException();
        }
        board.update(boardRequestDto);
        BoardContent boardContent = boardContentRepository.findByBoard(board);
        boardContent.update(boardRequestDto);

        List<BoardComment> boardComments = boardCommentRepository.findByBoard(board);
        List<GetBoardResponseDto> getBoardResponseDtos = boardComments.stream().map(boardComment -> new GetBoardResponseDto(boardComment)).toList();
        return new BoardResponseDto(board,boardContent,getBoardResponseDtos);
    }

    @Transactional
    public void deletedBoard(User user, long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(() -> new BoardPageNotFoundException());
        if (!user.getUsername().equals(board.getUser().getUsername())) {
            throw new BoardUnauthorizedException();
        }
        boardRepository.delete(board);
    }

    @Transactional
    public List<ViewBoardDto> getLikeBoard(User user) {
        List<BoardLike> boardlikes = boardLikeRepository.findByUser(user);
        List<ViewBoardDto> boards = boardlikes.stream().map(boardLike -> new ViewBoardDto(boardLike.getBoard())).collect(Collectors.toList());
        return boards;
    }
}

