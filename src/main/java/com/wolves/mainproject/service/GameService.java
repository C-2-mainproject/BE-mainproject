package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.game.history.GameHistoryRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.dto.response.GameHistoryDto;
import com.wolves.mainproject.exception.game.history.HistoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameService {
    private final GameHistoryRepository gameHistoryRepository;

    public GameHistoryDto findMyRecord(User user){
        return new GameHistoryDto(gameHistoryRepository.findByUser(user).orElseThrow(HistoryNotFoundException::new));
    }
}
