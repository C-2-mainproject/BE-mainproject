package com.wolves.mainproject.web;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.request.RequestMyWordStorageDto;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.MyWordStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MyWordStorageApiController {
    private final MyWordStorageService myWordStorageService;

    @AuthValidation
    @PostMapping("/api/user/wordstorage")
    public ResponseEntity<Void> createWordStorage(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody RequestMyWordStorageDto dto){
        myWordStorageService.insertWordStorage(principalDetails.getUser(), dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
