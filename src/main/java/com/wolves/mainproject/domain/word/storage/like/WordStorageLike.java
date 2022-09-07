package com.wolves.mainproject.domain.word.storage.like;

import com.wolves.mainproject.domain.common.CreateTimestamped;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.WordStorage;
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
@Table(name = "word_storage_like")
public class WordStorageLike extends CreateTimestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;

    @JoinColumn(name = "word_storage_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private WordStorage wordStorage;
}
