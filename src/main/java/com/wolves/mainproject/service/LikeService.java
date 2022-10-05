package com.wolves.mainproject.service;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.board.like.BoardLike;
import com.wolves.mainproject.domain.board.like.BoardLikeRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.exception.board.BoardPageNotFoundException;
import com.wolves.mainproject.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final BoardLikeRepository boardLikeRepository;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;


    @Transactional
    public void likeBoard(PrincipalDetails principalDetails, Long boardId) {
        User user = userRepository.findByUsername(principalDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        Board board = boardRepository.findById(boardId).orElseThrow(BoardPageNotFoundException::new);
        if(!boardLikeRepository.existsByUserAndBoard(user,board)){
            boardLikeRepository.save(BoardLike.builder().board(board).user(user).build());
        }else{
            boardLikeRepository.deleteByUserAndBoard(user, board);
        }

        long boardLike = boardLikeRepository.findByBoard(board).size();
        board.bringLikeCount(boardLike);

    }

}
