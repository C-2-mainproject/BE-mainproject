package com.wolves.mainproject.web;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.response.GameHistoryDto;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GameApiController {
    private final GameService gameService;

    @AuthValidation
    @GetMapping("/api/game/my/record")
    public ResponseEntity<GameHistoryDto> findMyRecord(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return new ResponseEntity<>(gameService.findMyRecord(principalDetails.getUser()), HttpStatus.OK);
    }

}
