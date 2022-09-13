package com.wolves.mainproject.service;

import com.wolves.mainproject.controller.dto.GetBoard;
import com.wolves.mainproject.controller.dto.request.BoardRequestDto;
import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.board.comment.BoardCommentRepository;
import com.wolves.mainproject.domain.board.content.BoardContent;
import com.wolves.mainproject.domain.board.content.BoardContentRepository;
import com.wolves.mainproject.domain.board.like.BoardLikeRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.exception.board.BoardPageNotFoundException;
import com.wolves.mainproject.exception.board.BoardTitleTooLargeException;
import com.wolves.mainproject.exception.board.BoardUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardContentRepository boardContentRepository;

    private final BoardCommentRepository boardCommentRepository;

    private final BoardLikeRepository boardLikeRepository;

    @Transactional(readOnly = true)
    public List<Board> getBoardAll() {
        List<Board> boards = boardRepository.findAll();
        return boards;
    }


    @Transactional(readOnly = true)
    public List<Board> searchBoard(BoardRequestDto title) {
        List<Board> board = boardRepository.findAllByTitleContaining(title.getTitle());
        return board;
    }

    @Transactional(readOnly = true)
    public GetBoard getBoardById(long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(()-> new BoardPageNotFoundException());
        BoardContent boardContent = boardContentRepository.findById(board_id).orElseThrow();
        return GetBoard.builder().board(board).boardContent(boardContent).build();
    }




    @Transactional
    public GetBoard creatBoard(User user, BoardRequestDto boardRequestDto) {
        if(boardRequestDto.getTitle().length()>40){
            throw new BoardTitleTooLargeException();
        }
        Board board = Board.builder().title(boardRequestDto.getTitle()).user(user).build();
        boardRepository.save(board);
        BoardContent boardContent = BoardContent.builder().board(board).content(boardRequestDto.getContent()).build();
        boardContentRepository.save(boardContent);
        return GetBoard.builder().board(board).boardContent(boardContent).build();
    }

    @Transactional
    public GetBoard updateBoard(User user, long board_id, BoardRequestDto boardRequestDto) {
        if(boardRequestDto.getTitle().length()>40){
            throw new BoardTitleTooLargeException();
        }
        Board board = boardRepository.findById(board_id).orElseThrow(()-> new BoardPageNotFoundException());
        if(!user.getUsername().equals(board.getUser().getUsername())){
            throw new BoardUnauthorizedException();
        }
        board.update(boardRequestDto);
        BoardContent boardContent = boardContentRepository.findByBoard(board);
        boardContent.update(boardRequestDto);
        return GetBoard.builder().board(board).boardContent(boardContent).build();
    }

    @Transactional
    public Board deletedBoard(User user, long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(()-> new BoardPageNotFoundException());
        if(!user.getUsername().equals(board.getUser().getUsername())){
            throw new BoardUnauthorizedException();
        }
        boardRepository.delete(board);
        return board;
    }



}

