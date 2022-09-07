package com.wolves.mainproject.domain.word;

import com.wolves.mainproject.domain.word.storage.WordStorage;
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
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String word;

    @Column(nullable = false)
    private String description;

    @JoinColumn(name = "word_storage_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private WordStorage wordStorage;
}
