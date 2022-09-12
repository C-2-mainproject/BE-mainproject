package com.wolves.mainproject.dto.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateWordDto {
    private List<String> words;
    private List<List<String>> meanings;
    private List<List<String>> pronunciations;
    private List<String> descriptions;


}
