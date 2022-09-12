package com.wolves.mainproject.domain.game.history;

import com.wolves.mainproject.domain.common.CreateTimestamped;
import com.wolves.mainproject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Entity
@Table(name = "game_history")
public class GameHistory extends CreateTimestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    private User user;

    @Column(name = "win_count", columnDefinition = "Bigint default 0")
    private long winCount;

    @Column(name = "lose_count", columnDefinition = "Bigint default 0")
    private long loseCount;
}
