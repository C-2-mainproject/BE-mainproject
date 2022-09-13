package com.wolves.mainproject.web;


import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @AuthValidation
    @PostMapping("/api/board/id/{board_id}/like") // 게시글 좋아요
    public void likeBoard(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long board_id) {
        likeService.likeBoard(principalDetails,board_id);
    }
}
