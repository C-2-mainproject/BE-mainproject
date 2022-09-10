package com.wolves.mainproject.dto.request;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FrequentlyQuestionDto {

    private String title;

    private String category;

    private String reply;
}
