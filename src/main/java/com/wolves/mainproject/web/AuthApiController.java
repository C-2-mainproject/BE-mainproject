package com.wolves.mainproject.web;

import com.wolves.mainproject.dto.request.RequestSignupDto;
import com.wolves.mainproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthApiController {
    private final AuthService authService;

    @PostMapping("/api/signup")
    public ResponseEntity<String> register(@RequestBody RequestSignupDto dto){
        authService.userRegister(dto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
