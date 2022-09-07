package com.wolves.mainproject.domain.word.storage;

import com.wolves.mainproject.domain.common.Timestamped;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Entity
@Table(name = "word_storage")
public class WordStorage extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false)
    private String description;

    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    private WordStorageCategory wordStorageCategory;

    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;

    @Column(nullable = false, name = "like_count")
    private long likeCount;

    @Column(name = "last_test_at")
    private LocalDateTime lastTestAt;


}
