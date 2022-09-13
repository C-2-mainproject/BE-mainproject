package com.wolves.mainproject.dto.WordStorageDto.response;

import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public class WordStorageDetailResponseDto {

    private Long id;
    private String title;
    private String description;
    private String category;
    private LocalDateTime createAt;
    private WordInfoDto words;

    public WordStorageDetailResponseDto(WordInfoDto words, WordStorage wordStorage){
        this.id = wordStorage.getId();
        this.title = wordStorage.getTitle();
        this.description = wordStorage.getDescription();
        this.category = wordStorage.getWordStorageCategory().getName();
        this.createAt = wordStorage.getCreateAt();
        this.words = words;

    }

}
