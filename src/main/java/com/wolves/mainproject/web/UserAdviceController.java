package com.wolves.mainproject.web;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.request.ReplyAdviceDto;
import com.wolves.mainproject.dto.request.UserAdviceDto;
import com.wolves.mainproject.handler.aop.annotation.AdminValidation;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.UserAdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserAdviceController {

    private final UserAdviceService userAdviceService;

    @AuthValidation
    @PostMapping("/api/support/advice")
    public ResponseEntity<?> createAdvice(@RequestBody UserAdviceDto requestDto,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return userAdviceService.createAdvice(requestDto, principalDetails);
    }

    @AdminValidation
    @PutMapping("/api/support/advice/id/{adviceId}")
    public ResponseEntity<?> updateAdvice(@PathVariable Long adviceId,
                                          @RequestBody ReplyAdviceDto requestDto,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return userAdviceService.updateAdvice(adviceId, requestDto, principalDetails);
    }
}
