package com.wolves.mainproject.web;


import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.request.board.BoardRequestDto;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/board")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping()
    public ResponseEntity<?> getBoardAll(){
        return new ResponseEntity<>(boardService.getBoardAll(), HttpStatus.OK );
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchBoard(@RequestBody BoardRequestDto boardRequestDto){

        return new ResponseEntity<>(boardService.searchBoard(boardRequestDto),  HttpStatus.OK);
    }

        @GetMapping("/id/{board_id}")
    public ResponseEntity<?> getBoard(@PathVariable long board_id){
        return new ResponseEntity<>(boardService.getBoardById(board_id),HttpStatus.OK);
    }

    @AuthValidation
    @PostMapping()
    public ResponseEntity<?> createBoard(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody BoardRequestDto boardRequestDto){
        return new ResponseEntity<>(boardService.creatBoard(principalDetails.getUser(), boardRequestDto),HttpStatus.OK);
    }


    @AuthValidation
    @PutMapping("/id/{board_id}")
    public ResponseEntity<?> updateBoard(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long board_id, @RequestBody BoardRequestDto boardRequestDto){
        return new ResponseEntity<>(boardService.updateBoard(principalDetails.getUser(),board_id, boardRequestDto), HttpStatus.OK);
    }


    @AuthValidation
    @DeleteMapping("/id/{board_id}")
    public ResponseEntity<?> deletedBoard(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long board_id){
        return new ResponseEntity<>(boardService.deletedBoard(principalDetails.getUser(), board_id),HttpStatus.OK);
    }

}
