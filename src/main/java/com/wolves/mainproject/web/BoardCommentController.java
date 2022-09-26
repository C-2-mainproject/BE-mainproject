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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/board/id/{boardId}/comment")
@RestController
@RequiredArgsConstructor
public class BoardCommentController {

    private final BoardCommentService boardCommentService;

    @AuthValidation
    @PostMapping()
    public ResponseEntity<?> createComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long boardId, @RequestBody @Valid BoardCommentRequestDto boardCommentRequestDto, BindingResult bindingResult) {
        return new ResponseEntity<>(boardCommentService.createComment(principalDetails.getUser(), boardId,boardCommentRequestDto),HttpStatus.OK);
    }

    @AuthValidation
    @PutMapping("/id/{commentId}")
    public ResponseEntity<?> updateComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long commentId,@RequestBody @Valid BoardCommentRequestDto boardCommentRequestDto, BindingResult bindingResult){
        boardCommentService.updateComment(principalDetails.getUser(), commentId, boardCommentRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AuthValidation
    @DeleteMapping("/id/{commentId}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long commentId){
        boardCommentService.deleteComment(principalDetails.getUser(),commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AuthValidation
    @PostMapping("/id/{commentId}/recomment")
    public ResponseEntity<?> replyComment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long boardId, @PathVariable BoardComment commentId, @RequestBody @Valid BoardCommentRequestDto boardCommentRequestDto, BindingResult bindingResult){
        return new ResponseEntity<>(boardCommentService.replyComment(principalDetails.getUser(),boardId,commentId, boardCommentRequestDto),HttpStatus.OK);
    }
}