package com.wolves.mainproject.dto.response;

import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.type.StatusType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WordStorageWithNoWordDto {
    private long id;
    private String title;
    private String description;
    private String category;
    private long likeCount;
    private boolean isPublic;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime lastTestAt;

    public WordStorageWithNoWordDto(WordStorage wordStorage){
        fromWordStorage(wordStorage);
    }

    public void fromWordStorage(WordStorage wordStorage){
        this.id = wordStorage.getId();
        this.title = wordStorage.getTitle();
        this.description = wordStorage.getDescription();
        this.category = wordStorage.getWordStorageCategory().getName();
        this.likeCount = wordStorage.getLikeCount();
        this.isPublic = wordStorage.getStatus().getValue();
        this.createAt = wordStorage.getCreateAt();
        this.modifiedAt = wordStorage.getModifiedAt();
        this.lastTestAt = wordStorage.getLastTestAt();
    }
}
