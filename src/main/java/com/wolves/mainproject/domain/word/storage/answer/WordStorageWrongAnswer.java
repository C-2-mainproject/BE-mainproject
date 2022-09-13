package com.wolves.mainproject.domain.word.storage.answer;

import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.wrong.answer.WrongAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@SuperBuilder
@Table(name = "word_storage_wrong_answer")
public class WordStorageWrongAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "word_storage_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    private WordStorage wordStorage;

    @JoinColumn(name = "wrong_answer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private WrongAnswer wrongAnswer;
}
