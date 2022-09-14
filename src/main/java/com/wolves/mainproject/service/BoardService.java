package com.wolves.mainproject.service;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.wolves.mainproject.domain.board.comment.BoardComment;
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


    @Transactional(readOnly = true)
    public List<ViewBoard> getBoardAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(board -> new ViewBoard(board)).toList();
    }


    @Transactional(readOnly = true)
    public List<ViewBoard> searchBoard(String search) {
        List<Board> boards = boardRepository.findByTitleContaining(search);
        return boards.stream().map(board -> new ViewBoard(board)).toList();
    }

    @Transactional
    public BoardResponseDto getBoardById(long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(()-> new BoardPageNotFoundException());
        BoardContent boardContent = boardContentRepository.findById(board_id).orElseThrow();
        List<BoardComment> boardComments = boardCommentRepository.findByBoard(board);
        List<GetBoardResponseDto> getBoardResponseDtos = boardComments.stream().map(boardComment -> new GetBoardResponseDto(boardComment)).toList();
        return BoardResponseDto.builder()
                .is_notice(board.isNotice())
                .title(board.getTitle())
                .likeCount(board.getLikeCount())
                .commentCount(board.getCommentCount())
                .content(boardContent.getContent())
                .createAt(board.getCreateAt())
                .comments(getBoardResponseDtos)
                .build();
    }




    @Transactional
    public GetBoardDto creatBoard(User user, BoardRequestDto boardRequestDto) {
        if(boardRequestDto.getTitle().length()>40){
            throw new BoardTitleTooLargeException();
        }
        if(user.getRole().equals(RoleType.ROLE_ADMIN)){
            Board board = Board.builder().title(boardRequestDto.getTitle()).user(user).build();
            boardRepository.save(board);
            BoardContent boardContent = BoardContent.builder().board(board).content(boardRequestDto.getContent()).build();
            boardContentRepository.save(boardContent);
            return GetBoardDto.builder()
                    .is_notice(true)
                    .title(board.getTitle())
                    .likeCount(board.getLikeCount())
                    .commentCount(board.getCommentCount())
                    .content(boardContent.getContent())
                    .createAt(board.getCreateAt())
                    .build();
        }
        else {
        Board board = Board.builder().title(boardRequestDto.getTitle()).user(user).build();
        boardRepository.save(board);
        BoardContent boardContent = BoardContent.builder().board(board).content(boardRequestDto.getContent()).build();
        boardContentRepository.save(boardContent);
        return GetBoardDto.builder()
                .is_notice(board.isNotice())
                .title(board.getTitle())
                .likeCount(board.getLikeCount())
                .commentCount(board.getCommentCount())
                .content(boardContent.getContent())
                .createAt(board.getCreateAt())
                .build();
        }

    }

    @Transactional
    public BoardResponseDto updateBoard(User user, long board_id, BoardRequestDto boardRequestDto) {
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

        List<BoardComment> boardComments = boardCommentRepository.findByBoard(board);
        List<GetBoardResponseDto> getBoardResponseDtos = boardComments.stream().map(boardComment -> new GetBoardResponseDto(boardComment)).toList();
        return BoardResponseDto.builder()
                .is_notice(board.isNotice())
                .title(board.getTitle())
                .likeCount(board.getLikeCount())
                .commentCount(board.getCommentCount())
                .createAt(board.getCreateAt())
                .content(boardContent.getContent())
                .comments(getBoardResponseDtos)
                .build();
    }

    @Transactional
    public void deletedBoard(User user, long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(()-> new BoardPageNotFoundException());
        if(!user.getUsername().equals(board.getUser().getUsername())){
            throw new BoardUnauthorizedException();
        }
        boardRepository.delete(board);
    }
}

