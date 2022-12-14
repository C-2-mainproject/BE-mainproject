package com.wolves.mainproject.service;

import com.wolves.mainproject.dto.request.board.BoardCommentRequestDto;
import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.board.comment.BoardComment;
import com.wolves.mainproject.domain.board.comment.BoardCommentRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.dto.response.BoardCommentResponseDto;
import com.wolves.mainproject.exception.board.BoardCommentNotFoundException;
import com.wolves.mainproject.exception.board.BoardCommentUnauthorizedException;
import com.wolves.mainproject.exception.board.BoardPageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardCommentService {

    private final BoardRepository boardRepository;

    private final BoardCommentRepository boardCommentRepository;

    // @TODO : Need change to use jpa only
    private BoardComment getBoardCommentWithCredential(User user, long commentId){
        BoardComment boardComment = boardCommentRepository.findById(commentId).orElseThrow(BoardCommentNotFoundException::new);
        if(user.getId() != boardComment.getUser().getId()){
            throw new BoardCommentUnauthorizedException();
        }
        return boardComment;
    }

    // @TODO : Need change to use jpa only
    private void commentSaveAndCount(Board board, BoardComment boardComment, long boardId){
        boardCommentRepository.save(boardComment);
        long commentCount = boardCommentRepository.findByBoardId(boardId).size();
        board.getCommentCount(commentCount);
    }

    //λκΈ μμ±
    @Transactional
    public BoardCommentResponseDto createComment(User user, long boardId, BoardCommentRequestDto boardCommentRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardPageNotFoundException::new);
        BoardComment boardComment = BoardComment.builder().board(board).status(true).user(user).refer(null).content(boardCommentRequestDto.getContent()).build();
        commentSaveAndCount(board, boardComment, boardId);
        return new BoardCommentResponseDto(boardComment.getId(), boardComment.getContent());
    }

    //λκΈ μμ 
    @Transactional
    public void updateComment(User user, long commentId, BoardCommentRequestDto boardCommentRequestDto) {
        BoardComment boardComment = getBoardCommentWithCredential(user, commentId);
        boardComment.update(boardCommentRequestDto);

    }

    //λκΈ μ­μ 
    @Transactional
    public BoardComment deleteComment(User user, long commentId){
        BoardComment boardComment = getBoardCommentWithCredential(user, commentId);
        boardComment.delete();
        return boardComment;
    }

    //λλκΈ μμ±
    @Transactional
    public BoardCommentResponseDto replyComment(User user, long boardId, BoardComment commentId, BoardCommentRequestDto boardCommentRequestDto){
        Board board = boardRepository.findById(boardId).orElseThrow(BoardPageNotFoundException::new);
        BoardComment boardComment = BoardComment.builder().board(board).status(true).user(user).refer(commentId).content(boardCommentRequestDto.getContent()).build();
        commentSaveAndCount(board, boardComment, boardId);
        return new BoardCommentResponseDto(boardComment.getId(),boardComment.getContent());
    }
}