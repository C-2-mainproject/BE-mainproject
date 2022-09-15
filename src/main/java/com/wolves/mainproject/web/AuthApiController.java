package com.wolves.mainproject.web;

import com.wolves.mainproject.dto.request.my.word.storage.RequestSignupDto;
import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import com.wolves.mainproject.exception.board.BoardCommentNotFoundException;
import com.wolves.mainproject.exception.board.BoardCommentTooLargeException;
import com.wolves.mainproject.exception.user.UserNotFoundException;
import com.wolves.mainproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthApiController {
    private final AuthService authService;

    @PostMapping("/api/signup")
    public ResponseEntity<Void> register(@RequestBody @Valid RequestSignupDto dto, BindingResult bindingResult) {
        authService.userRegister(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
