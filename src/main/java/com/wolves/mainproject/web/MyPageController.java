package com.wolves.mainproject.web;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.request.UserDto;
import com.wolves.mainproject.handler.aop.annotation.AdminValidation;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    @AuthValidation
    @GetMapping("/api/user")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return myPageService.getUserInfo(principalDetails);
    }

    @AuthValidation
    @PutMapping("/api/user")
    public ResponseEntity<?> updateUser(@RequestBody UserDto requestDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return myPageService.updateUser(requestDto, principalDetails);
    }

    @AuthValidation
    @DeleteMapping("/api/user")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return myPageService.deleteUser(principalDetails);
    }

    @AuthValidation
    @GetMapping("/api/user/question")
    public ResponseEntity<?> getUserQuestion(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return new ResponseEntity<>(myPageService.getUserQuestion(principalDetails), HttpStatus.OK);
    }


    @AdminValidation
    @GetMapping("/api/admin/question")
    public ResponseEntity<?> getAdminQuestion(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return new ResponseEntity<>(myPageService.getAdminQuestion(principalDetails), HttpStatus.OK);
    }
}
