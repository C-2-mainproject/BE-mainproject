package com.wolves.mainproject.web;


import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.request.board.BoardRequestDto;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/board")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping()
    public ResponseEntity<?> getBoardAll(){
        return new ResponseEntity<>(boardService.getBoardAll(), HttpStatus.OK );
    }


    @GetMapping("/title")
    public ResponseEntity<?> searchBoard(@RequestParam String search){
        return new ResponseEntity<>(boardService.searchBoard(search),  HttpStatus.OK);
    }

        @GetMapping("/id/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable long boardId){
        return new ResponseEntity<>(boardService.getBoardById(boardId),HttpStatus.OK);
    }

    @AuthValidation
    @PostMapping()
    public ResponseEntity<?> createBoard(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Valid BoardRequestDto boardRequestDto, BindingResult bindingResult){
        return new ResponseEntity<>(boardService.createBoard(principalDetails.getUser(), boardRequestDto),HttpStatus.OK);
    }


    @AuthValidation
    @PutMapping("/id/{boardId}")
    public ResponseEntity<?> updateBoard(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long boardId, @RequestBody @Valid BoardRequestDto boardRequestDto, BindingResult bindingResult){
        return new ResponseEntity<>(boardService.updateBoard(principalDetails.getUser(),boardId, boardRequestDto), HttpStatus.OK);
    }


    @AuthValidation
    @DeleteMapping("/id/{boardId}")
    public ResponseEntity<?> deletedBoard(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long boardId){
        boardService.deletedBoard(principalDetails.getUser(), boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AuthValidation
    @GetMapping("/user/like")
    public  ResponseEntity<?> getLikeBoard(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return new ResponseEntity<>(boardService.getLikeBoard(principalDetails.getUser()),HttpStatus.OK);
    }
}
