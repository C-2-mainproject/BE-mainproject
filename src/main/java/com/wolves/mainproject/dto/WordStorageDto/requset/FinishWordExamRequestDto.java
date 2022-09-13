package com.wolves.mainproject.dto.WordStorageDto.requset;

import com.wolves.mainproject.dto.WordStorageDto.response.WordInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
