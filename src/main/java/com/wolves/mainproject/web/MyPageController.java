package com.wolves.mainproject.web;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.request.PasswordDto;
import com.wolves.mainproject.dto.request.UserDto;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    @AuthValidation
    @PostMapping("/api/user")
    public ResponseEntity<?> getUserToPassword(@RequestBody PasswordDto requestDto,
                                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return myPageService.getUserToPassword(requestDto, principalDetails);
    }

    @AuthValidation
    @PutMapping("/api/user")
    public ResponseEntity<?> updateUser(@RequestBody UserDto requestDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return myPageService.updateUser(requestDto, principalDetails);
    }

    @AuthValidation
    @PutMapping("/api/user/password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto requestDto,
                                            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return myPageService.updatePassword(requestDto, principalDetails);
    }

    @AuthValidation
    @DeleteMapping("/api/user")
    public ResponseEntity<?> deleteUser(@RequestBody PasswordDto requestDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        return myPageService.deleteUser(requestDto, principalDetails);
    }
}
