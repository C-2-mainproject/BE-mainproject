package com.wolves.mainproject.web;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.request.board.BoardCommentRequestDto;
import com.wolves.mainproject.domain.board.comment.BoardComment;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/board/id/{board_id}/comment")
@RestController
@RequiredArgsConstructor
public class BoardCommentController {

    private final BoardCommentService boardCommentService;

    @AuthValidation
    @PostMapping()
    public ResponseEntity<?> createComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long board_id, @RequestBody BoardCommentRequestDto boardCommentRequestDto) {
        return new ResponseEntity<>(boardCommentService.createComment(principalDetails.getUser(), board_id,boardCommentRequestDto),HttpStatus.OK);
    }

    @AuthValidation
    @PutMapping("/id/{comment_id}")
    public ResponseEntity<?> updateComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long comment_id,@RequestBody BoardCommentRequestDto boardCommentRequestDto){
        return new ResponseEntity<>(boardCommentService.updateComment(principalDetails.getUser(), comment_id, boardCommentRequestDto), HttpStatus.OK);
    }

    @AuthValidation
    @DeleteMapping("/id/{comment_id}")
    public ResponseEntity<?> deletedComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long comment_id){

        return new ResponseEntity<>(boardCommentService.deleteComment(principalDetails.getUser(),comment_id),HttpStatus.OK);
    }

    @AuthValidation
    @PostMapping("/id/{comment_id}")
    public ResponseEntity<?> replyComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long board_id, @PathVariable BoardComment comment_id, @RequestBody BoardCommentRequestDto boardCommentRequestDto){
        return new ResponseEntity<>(boardCommentService.replyComment(principalDetails.getUser(),board_id,comment_id, boardCommentRequestDto),HttpStatus.OK);
    }




}