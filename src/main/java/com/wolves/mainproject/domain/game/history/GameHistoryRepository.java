package com.wolves.mainproject.domain.game.history;

import com.wolves.mainproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    @Query(value = "SELECT *, rank() over(order by win_count desc) as ranking from game_history limit 8", nativeQuery = true)
    List<GameHistory> findAllByRanking();

    Optional<GameHistory> findByUser(User user);
}
