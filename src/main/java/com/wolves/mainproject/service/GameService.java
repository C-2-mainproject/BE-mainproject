package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.game.history.GameHistory;
import com.wolves.mainproject.domain.game.history.GameHistoryRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.dto.response.GameHistoryDto;
import com.wolves.mainproject.dto.response.RankingByGameHistoryDto;
import com.wolves.mainproject.exception.game.history.HistoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {
    private final GameHistoryRepository gameHistoryRepository;

    public GameHistoryDto findMyRecord(User user){
        return new GameHistoryDto(gameHistoryRepository.findByUser(user).orElseThrow(HistoryNotFoundException::new));
    }

    public List<RankingByGameHistoryDto> findRanking(){
        List<GameHistory> gameHistories = gameHistoryRepository.findAllByRanking();
        return gameHistories.stream().map(RankingByGameHistoryDto::new).toList();
    }
}
