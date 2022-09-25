package com.wolves.mainproject.service;

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
import com.wolves.mainproject.exception.board.BoardUnauthorizedException;
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

    private final BoardLikeRepository boardLikeRepository;



    // @TODO : Need change to use jpa only
    private Board getBoardWithCredential(User user, long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(BoardPageNotFoundException::new);
        if (user.getId() != board.getUser().getId())
            throw new BoardUnauthorizedException();
        return board;
    }

    // @TODO : Need change to use jpa only
    private List<GetBoardResponseDto> getBoardResponseDtoList(Board board){
        List<BoardComment> boardComments = boardCommentRepository.findByBoard(board);
        List<GetBoardResponseDto> getBoardResponseDtos = boardComments.stream().map(boardComment -> new GetBoardResponseDto(boardComment)).toList();
        return getBoardResponseDtos;
    }

    //게시판 조회
    @Transactional(readOnly = true)
    public List<ViewBoardDto> getBoardAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(board -> new ViewBoardDto(board)).toList();
    }

    //게시판 내 제목이나 유저로 검색
    @Transactional(readOnly = true)
    public List<ViewBoardDto> searchBoard(String search) {
        List<Board> boardsList = boardRepository.findByTitleContainingOrUserNicknameContaining(search);
        return boardsList.stream().map(board -> new ViewBoardDto(board)).toList();
    }

    //게시글 상세조회
    @Transactional
    public BoardResponseDto getBoardById(long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardPageNotFoundException());
        BoardContent boardContent = boardContentRepository.findById(boardId).orElseThrow(BoardPageNotFoundException::new);
        return new BoardResponseDto(board,boardContent,getBoardResponseDtoList(board));
    }

    //게시글 만들기
    @Transactional
    public GetBoardDto createBoard(User user, BoardRequestDto boardRequestDto) {
        Board board = Board.builder().title(boardRequestDto.getTitle()).user(user).build();
        boardRepository.save(board);
        BoardContent boardContent = BoardContent.builder().board(board).content(boardRequestDto.getContent()).build();
        boardContentRepository.save(boardContent);
        return new GetBoardDto(user, board, boardContent);
    }

    //게시글 업데이트
    @Transactional
    public BoardResponseDto updateBoard(User user, long boardId, BoardRequestDto boardRequestDto) {
        Board board = getBoardWithCredential(user,boardId);
        board.update(boardRequestDto);
        BoardContent boardContent = boardContentRepository.findByBoard(board);
        boardContent.update(boardRequestDto);
        return new BoardResponseDto(board,boardContent,getBoardResponseDtoList(board));
    }

    //게시글 삭제
    @Transactional
    public void deletedBoard(User user, long boardId) {
        Board board = getBoardWithCredential(user,boardId);
        boardRepository.delete(board);
    }

    //좋아요한 게시글 조회
    @Transactional
    public List<ViewBoardDto> getLikeBoard(User user) {
        List<BoardLike> boardlikes = boardLikeRepository.findByUser(user);
        List<ViewBoardDto> boards = boardlikes.stream().map(boardLike -> new ViewBoardDto(boardLike.getBoard())).collect(Collectors.toList());
        return boards;
    }
}