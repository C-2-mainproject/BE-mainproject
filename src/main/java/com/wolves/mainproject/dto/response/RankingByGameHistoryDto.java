package com.wolves.mainproject.dto.response;

import com.wolves.mainproject.domain.game.history.GameHistory;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RankingByGameHistoryDto {
    private String nickname;
    private String profileImage;
    private long winCount;

    public RankingByGameHistoryDto(GameHistory gameHistory){
        this.nickname = gameHistory.getUser().getNickname();
        this.profileImage = gameHistory.getUser().getProfileImage();
        this.winCount = gameHistory.getWinCount();
    }
}
