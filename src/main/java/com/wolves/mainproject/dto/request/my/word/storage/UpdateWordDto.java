package com.wolves.mainproject.dto.request.my.word.storage;

import com.wolves.mainproject.domain.dynamo.word.Word;
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

    public Word toWord(long id){
        return Word.builder()
                .wordStorageId(id)
                .words(words)
                .meanings(meanings)
                .build();
    }
}
