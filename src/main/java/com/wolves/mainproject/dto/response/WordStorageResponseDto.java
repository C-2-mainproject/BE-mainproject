package com.wolves.mainproject.dto.response;

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

    public WordStorageResponseDto(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
