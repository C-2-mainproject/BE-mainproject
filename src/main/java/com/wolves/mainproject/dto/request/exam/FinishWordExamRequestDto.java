package com.wolves.mainproject.dto.request.exam;

import com.wolves.mainproject.dto.response.WordInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FinishWordExamRequestDto {
    Long wordStorageId;
    String testType;
    int totalWords;
    int wrongWords;
    Long time;
    WordInfoDto collectionWrongWord;

}
