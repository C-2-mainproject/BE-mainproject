package com.wolves.mainproject.service;

import com.wolves.mainproject.controller.dto.BoardRequestDto;
import com.wolves.mainproject.controller.dto.BoardResponseDto;
import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.board.comment.BoardComment;
import com.wolves.mainproject.domain.board.comment.BoardCommentRepository;
import com.wolves.mainproject.domain.board.content.BoardContent;
import com.wolves.mainproject.domain.board.content.BoardContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardContentRepository boardContentRepository;

    private final BoardCommentRepository boardCommentRepository;

    public List<Board> getBoardAll() {
        List<Board> boards = boardRepository.findAll();
        return boards;
    }


    public List<Board> searchBoard(BoardRequestDto title) {
        List<Board> board = boardRepository.findAllByTitleContaining(title.getTitle());
        return board;
    }

    public Board getBoardById(long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow();
//        BoardContent boardContent = boardContentRepository.findById(board_id).orElseThrow()
        return board;
    }




    @Transactional
    public Board creatBoard(BoardRequestDto boardRequestDto) {
        Board board = Board.builder().title(boardRequestDto.getTitle()).build();
        boardRepository.save(board);
        return board;
    }

    @Transactional
    public Board updateBoard(long board_id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(board_id).orElseThrow();
        board.update(boardRequestDto);
        return board;
    }

    @Transactional
    public Board deletedBoard(long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow();
        boardRepository.delete(board);
        return board;
    }



}

