package com.wolves.mainproject.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WordDto {
    private List<String> words;
    private List<List<String>> meanings;
}
