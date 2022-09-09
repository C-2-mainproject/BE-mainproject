package com.wolves.mainproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> proceedAllException(Exception e){
        return new ResponseEntity<>(String.format("<h1>%s</h1>", e.getMessage()), HttpStatus.EXPECTATION_FAILED);
    }
}
