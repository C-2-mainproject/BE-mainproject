package com.wolves.mainproject.domain.word.storage;

import com.wolves.mainproject.domain.common.Timestamped;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.dto.request.PostBookmarkedWordStorageDto;
import com.wolves.mainproject.dto.request.RequestMyWordStorageDto;
import com.wolves.mainproject.dto.request.UpdateMyWordStorageStatusDto;
import com.wolves.mainproject.type.StatusType;
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

    @Column(nullable = false, columnDefinition = "boolean default false", name = "is_bookmarked")
    private boolean isBookmarked;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType status;

    @Column(name = "last_test_at")
    private LocalDateTime lastTestAt;

    public void update(RequestMyWordStorageDto dto, WordStorageCategory category){
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.status = StatusType.findByBoolean(dto.isStatus());
        this.wordStorageCategory = category;
    }

    public void update(UpdateMyWordStorageStatusDto dto){
        this.status = StatusType.findByBoolean(dto.isStatus());
    }
    public void update(PostBookmarkedWordStorageDto dto) { this.isBookmarked = dto.isStatus(); }
}
