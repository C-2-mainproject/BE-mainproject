package com.wolves.mainproject.web;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.request.game.history.PostGameHistoryDto;
import com.wolves.mainproject.dto.response.GameHistoryDto;
import com.wolves.mainproject.dto.response.RankingByGameHistoryDto;
import com.wolves.mainproject.dto.response.TicketDto;
import com.wolves.mainproject.dto.response.WordDto;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class GameApiController {
    private final GameService gameService;

    @AuthValidation
    @GetMapping("/api/game/my/record")
    public ResponseEntity<GameHistoryDto> findMyRecord(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return new ResponseEntity<>(gameService.findMyRecord(principalDetails.getUser()), HttpStatus.OK);
    }

    @AuthValidation
    @GetMapping("/api/game/rank")
    public ResponseEntity<List<RankingByGameHistoryDto>> findRanking(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return new ResponseEntity<>(gameService.findRanking(), HttpStatus.OK);
    }

    @AuthValidation
    @PostMapping("/api/game/result")
    public ResponseEntity<Void> postGameResult(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody PostGameHistoryDto dto){
        gameService.postGameResult(principalDetails.getUser(), dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/game/word")
    public ResponseEntity<WordDto> findWordForRoomId(@RequestParam String roomId){
        return new ResponseEntity<>(gameService.getWordFromRoomId(roomId), HttpStatus.OK);
    }

    @AuthValidation
    @GetMapping("/api/game/ticket")
    public ResponseEntity<TicketDto> certificationGameUser(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request){
        return new ResponseEntity<>(gameService.certificationGameUser(request), HttpStatus.OK);
    }
}
