package com.wolves.mainproject.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class healthyCheckController {


    @GetMapping("/healthyCheck")
    public ResponseEntity<Object> awsHealthyCheck(){
        return ResponseEntity.ok("awsHealthyCheck - ok");
    }
}