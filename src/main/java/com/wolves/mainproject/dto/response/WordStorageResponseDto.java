package com.wolves.mainproject.dto.response;

import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageMapping;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WordStorageResponseDto {
    private Long id;
    private String title;
    private String description;
    private boolean haveStorage;

    public WordStorageResponseDto(WordStorage wordStorage) {
        this.id = wordStorage.getId();
        this.title = wordStorage.getTitle();
        this.description = wordStorage.getDescription();
    }
}
