package com.wolves.mainproject.dto.response;

import com.wolves.mainproject.domain.game.history.GameHistory;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GameHistoryDto {
    private long winCount;
    private long loseCount;

    public GameHistoryDto(GameHistory gameHistory){
        toGameHistory(gameHistory);
    }

    private void toGameHistory(GameHistory gameHistory){
        this.winCount = gameHistory.getWinCount();
        this.loseCount = gameHistory.getLoseCount();
    }
}
