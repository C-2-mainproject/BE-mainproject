package com.wolves.mainproject.service;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.controller.dto.request.BoardCommentRequestDto;
import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.board.comment.BoardComment;
import com.wolves.mainproject.domain.board.comment.BoardCommentRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.exception.board.BoardCommentNotFoundException;
import com.wolves.mainproject.exception.board.BoardCommentTooLargeException;
import com.wolves.mainproject.exception.board.BoardCommentUnauthorizedException;
import com.wolves.mainproject.exception.board.BoardUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardCommentService {

    private final BoardRepository boardRepository;

    private final BoardCommentRepository boardCommentRepository;

    @Transactional
    public BoardComment createComment(User user, long board_id, BoardCommentRequestDto boardCommentRequestDto) {
        if(boardCommentRequestDto.getContent().length()>255){
            throw new BoardCommentTooLargeException();
        }
        Board board = boardRepository.findById(board_id).orElseThrow();
        BoardComment boardComment = BoardComment.builder().board(board).status(true).user(user).refer(null).content(boardCommentRequestDto.getContent()).build();
        boardCommentRepository.save(boardComment);
        long commentCount = boardCommentRepository.findByBoard(board).size();
        board.getCommentCount(commentCount);
        return  boardComment;
    }

    @Transactional
    public BoardComment updateComment(User user, long comment_id, BoardCommentRequestDto boardCommentRequestDto) {
        if(boardCommentRequestDto.getContent().length()>255){
            throw new BoardCommentTooLargeException();
        }
        BoardComment boardComment = boardCommentRepository.findById(comment_id).orElseThrow(()-> new BoardCommentNotFoundException());
        if(!user.getUsername().equals(boardComment.getUser().getUsername())){
            throw new BoardCommentUnauthorizedException();
        }
        boardComment.update(boardCommentRequestDto);
        return boardComment;
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
    public BoardComment replyComment(User user, long board_id, BoardComment comment_id, BoardCommentRequestDto boardCommentRequestDto){
        if(boardCommentRequestDto.getContent().length()>255){
            throw new BoardCommentTooLargeException();
        }
        Board board = boardRepository.findById(board_id).orElseThrow();
        BoardComment boardComment = BoardComment.builder().board(board).status(true).user(user).refer(comment_id).content(boardCommentRequestDto.getContent()).build();
        boardCommentRepository.save(boardComment);
        long commentCount = boardCommentRepository.findByBoard(board).size();
        board.getCommentCount(commentCount);
        return  boardComment;

    }


}