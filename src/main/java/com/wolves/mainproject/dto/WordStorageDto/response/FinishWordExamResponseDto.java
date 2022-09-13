package com.wolves.mainproject.dto.WordStorageDto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinishWordExamResponseDto {

    private Long wordStorageId;

    public FinishWordExamResponseDto(Long wordStorageId) {
        this.wordStorageId = wordStorageId;
    }
}
