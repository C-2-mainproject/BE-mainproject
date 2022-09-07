package com.wolves.mainproject.domain.game.history;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
}
