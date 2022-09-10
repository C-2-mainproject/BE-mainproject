package com.wolves.mainproject.controller;


import com.wolves.mainproject.controller.dto.BoardRequestDto;
import com.wolves.mainproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping()
    public ResponseEntity<?> createBoard(@RequestBody BoardRequestDto boardRequestDto){
        return new ResponseEntity<>(boardService.creatBoard(boardRequestDto),HttpStatus.OK);
    }


    @PutMapping("/id/{board_id}")
    public ResponseEntity<?> updateBoard(@PathVariable long board_id, @RequestBody BoardRequestDto boardRequestDto){
        return new ResponseEntity<>( boardService.updateBoard(board_id, boardRequestDto), HttpStatus.OK);
    }


    @DeleteMapping("/id/{board_id}")
    public ResponseEntity<?> deletedBoard(@PathVariable long board_id){
        return new ResponseEntity<>(boardService.deletedBoard(board_id),HttpStatus.OK) ;
    }

}
