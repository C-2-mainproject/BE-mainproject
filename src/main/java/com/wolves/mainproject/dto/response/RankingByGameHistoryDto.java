package com.wolves.mainproject.dto.response;

import com.wolves.mainproject.domain.game.history.GameHistory;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RankingByGameHistoryDto {
    private String username;
    private long winCount;

    public RankingByGameHistoryDto(GameHistory gameHistory){
        this.username = gameHistory.getUser().getUsername();
        this.winCount = gameHistory.getWinCount();
    }
}
