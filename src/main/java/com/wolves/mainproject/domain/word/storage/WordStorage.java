package com.wolves.mainproject.domain.word.storage;

import com.wolves.mainproject.domain.common.Timestamped;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLike;
import com.wolves.mainproject.dto.request.my.word.storage.PostBookmarkedWordStorageDto;
import com.wolves.mainproject.dto.request.my.word.storage.RequestMyWordStorageDto;
import com.wolves.mainproject.dto.request.my.word.storage.UpdateMyWordStorageStatusDto;
import com.wolves.mainproject.exception.CustomException;
import com.wolves.mainproject.exception.ErrorCode;
import com.wolves.mainproject.exception.wordStorage.WordStorageNotValidException;
import com.wolves.mainproject.type.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "word_storage", indexes = {@Index(name = "status_index", columnList = "status")})
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

    @Column(nullable = false, columnDefinition="bigint default 0", name = "like_count")
    private long likeCount;

    @Column(nullable = true, columnDefinition = "boolean default false", name = "is_bookmarked")
    private boolean isBookmarked;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType status;

    @Column(name = "last_test_at")
    private LocalDateTime lastTestAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_wordstorage")
    private WordStorage originalWordStorage;

    public WordStorage(User user, WordStorage wordStorage, WordStorageCategory category, StatusType status) {
        this.title = wordStorage.getTitle();
        this.description = wordStorage.getDescription();
        this.wordStorageCategory = category;
        this.user = user;
        this.likeCount = 0;
        this.status = status;
        this.originalWordStorage = wordStorage;
    }

    public void update(RequestMyWordStorageDto dto, WordStorageCategory category, User user){
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.status = StatusType.getStatusByUser(dto.isStatus(), user);
        this.wordStorageCategory = category;
    }

    public void update(UpdateMyWordStorageStatusDto dto, WordStorage wordStorage){
        if (wordStorage.getOriginalWordStorage() != null)
            throw new WordStorageNotValidException();
        this.status = StatusType.getStatus(dto.isStatus());
    }
    public void update(PostBookmarkedWordStorageDto dto) { this.isBookmarked = dto.isStatus(); }

    public void update(long likeCount) {
        this.likeCount = likeCount;
    }
}
