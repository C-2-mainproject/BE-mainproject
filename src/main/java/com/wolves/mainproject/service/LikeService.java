package com.wolves.mainproject.service;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.board.Board;
import com.wolves.mainproject.domain.board.BoardRepository;
import com.wolves.mainproject.domain.board.like.BoardLike;
import com.wolves.mainproject.domain.board.like.BoardLikeRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
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
    public void likeBoard(PrincipalDetails principalDetails, Long board_id) {
        User user = userRepository.findByUsername(principalDetails.getUsername()).orElseThrow();
        Board board = boardRepository.findById(board_id).orElseThrow();
        if(!boardLikeRepository.existsByUserAndBoard(user,board)){
            boardLikeRepository.save(BoardLike.builder().board(board).user(user).build());
        }else{
            boardLikeRepository.deleteByUserAndBoard(user, board);
        }

        long boardLike = boardLikeRepository.findByBoard(board).size();
        board.bringLikeCount(boardLike);

    }

}
