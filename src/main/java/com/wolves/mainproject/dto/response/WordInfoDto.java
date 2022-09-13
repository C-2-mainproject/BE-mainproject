package com.wolves.mainproject.dto.response;

import com.wolves.mainproject.domain.dynamo.word.Word;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public class WordInfoDto {

    private List<String> word;
    private List<List<String>> meaning;

    public WordInfoDto(Word wordList) {
        this.word = wordList.getWords();
        this.meaning = wordList.getMeanings();
    }

}
