package com.wolves.mainproject.controller;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.handler.aop.annotation.AdminValidation;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MyPageService myPageService;


    @AuthValidation
    @GetMapping("/api/user/question")
    public ResponseEntity<?> getUserQuestion(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return new ResponseEntity<>(myPageService.getUserQuestion(principalDetails), HttpStatus.OK);
    }


}
