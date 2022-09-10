package com.wolves.mainproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllFrequentlyQuestionResponseDto {

    private String title;
    private String category;
    private String  reply;
}
