package com.wolves.mainproject.service;

import com.wolves.mainproject.dto.request.board.BoardCommentRequestDto;
import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.board.comment.BoardComment;
import com.wolves.mainproject.domain.board.comment.BoardCommentRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.dto.response.BoardCommentResponseDto;
import com.wolves.mainproject.exception.board.BoardCommentNotFoundException;
import com.wolves.mainproject.exception.board.BoardCommentTooLargeException;
import com.wolves.mainproject.exception.board.BoardCommentUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardCommentService {

    private final BoardRepository boardRepository;

    private final BoardCommentRepository boardCommentRepository;

    @Transactional
    public BoardCommentResponseDto createComment(User user, long board_id, BoardCommentRequestDto boardCommentRequestDto) {
        if(boardCommentRequestDto.getContent().length()>255){
            throw new BoardCommentTooLargeException();
        }
        Board board = boardRepository.findById(board_id).orElseThrow();
        BoardComment boardComment = BoardComment.builder().board(board).status(true).user(user).refer(null).content(boardCommentRequestDto.getContent()).build();
        boardCommentRepository.save(boardComment);
        long commentCount = boardCommentRepository.findByBoardId(board_id).size();
        board.getCommentCount(commentCount);
        return new BoardCommentResponseDto(boardComment.getId(), boardComment.getContent());
    }

    @Transactional
    public void updateComment(User user, long comment_id, BoardCommentRequestDto boardCommentRequestDto) {
        if(boardCommentRequestDto.getContent().length()>255){
            throw new BoardCommentTooLargeException();
        }
        BoardComment boardComment = boardCommentRepository.findById(comment_id).orElseThrow(()-> new BoardCommentNotFoundException());
        if(!user.getUsername().equals(boardComment.getUser().getUsername())){
            throw new BoardCommentUnauthorizedException();
        }
        boardComment.update(boardCommentRequestDto);

    }

    @Transactional
    public BoardComment deleteComment(User user, long comment_id){
        BoardComment boardComment = boardCommentRepository.findById(comment_id).orElseThrow(()-> new BoardCommentNotFoundException());
        if(!user.getUsername().equals(boardComment.getUser().getUsername())){
            throw new BoardCommentUnauthorizedException();
        }
        boardComment.delete();
        return boardComment;
    }

    @Transactional
    public BoardCommentResponseDto replyComment(User user, long board_id, BoardComment comment_id, BoardCommentRequestDto boardCommentRequestDto){
        if(boardCommentRequestDto.getContent().length()>255){
            throw new BoardCommentTooLargeException();
        }
        Board board = boardRepository.findById(board_id).orElseThrow();
        BoardComment boardComment = BoardComment.builder().board(board).status(true).user(user).refer(comment_id).content(boardCommentRequestDto.getContent()).build();
        boardCommentRepository.save(boardComment);
        long commentCount = boardCommentRepository.findByBoardId(board_id).size();
        board.getCommentCount(commentCount);
        return new BoardCommentResponseDto(boardComment.getId(),boardComment.getContent());
    }
}