package com.wolves.mainproject.dto.request.exam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WordExamRequestDto {
    private String testType;
    private Long wordStorageId;

}
