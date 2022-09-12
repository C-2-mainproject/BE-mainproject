package com.wolves.mainproject.domain.game.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    @Query(value = "SELECT *, rank() over(order by win_count desc) as ranking from game_history limit 10", nativeQuery = true)
    List<GameHistory> findAllByRanking();
}
