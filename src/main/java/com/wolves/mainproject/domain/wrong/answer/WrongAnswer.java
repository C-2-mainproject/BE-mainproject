package com.wolves.mainproject.domain.wrong.answer;

import com.wolves.mainproject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "wrong_answer")
public class WrongAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "test_type", nullable = false, length = 45)
    private String testType;

    @Column(name = "total_words", nullable = false)
    private int totalWords;

    @Column(name = "wrong_words", nullable = false)
    private int wrongWords;

    @Column(name = "time", nullable = false)
    private long time;

    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;
}
