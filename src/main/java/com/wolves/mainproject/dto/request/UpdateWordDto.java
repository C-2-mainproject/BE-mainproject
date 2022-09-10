package com.wolves.mainproject.dto.request;

import com.wolves.mainproject.domain.meaning.Meaning;
import com.wolves.mainproject.domain.prononciation.Prononciation;
import com.wolves.mainproject.domain.word.Word;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import lombok.*;

import java.util.ArrayList;
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

    public List<Word> toWords(WordStorage wordStorage){
        List<Word> result = new ArrayList<>();
        descriptions.forEach(description -> words.forEach(word -> result.add(Word.builder().word(word).wordStorage(wordStorage).description(description).build())));
        return result;
    }

    public List<Meaning> toMeanings(List<Word> words){
        List<Meaning> result = new ArrayList<>();
        meanings.forEach(meaning -> meaning.forEach(mean -> words.forEach(word -> result.add(Meaning.builder().word(word).meaning(mean).build()))));
        return result;
    }

    public List<Prononciation> toPronunciations(List<Word> words){
        List<Prononciation> result = new ArrayList<>();
        pronunciations.forEach(pronunciation -> pronunciation.forEach(pron -> words.forEach(word -> result.add(Prononciation.builder().word(word).prononciation(pron).build()))));
        return result;
    }

}
