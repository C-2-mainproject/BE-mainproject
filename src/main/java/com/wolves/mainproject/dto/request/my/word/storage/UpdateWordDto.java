package com.wolves.mainproject.dto.request.my.word.storage;

import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.exception.ErrorCode;
import com.wolves.mainproject.exception.word.WordNotAcceptableException;
import com.wolves.mainproject.handler.aop.annotation.domain.ListLengthValidation;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateWordDto {
    @ListLengthValidation(length = 200, exception = ErrorCode.WORD_NOT_ACCEPTABLE)
    private List<String> words;
    @ListLengthValidation(length = 200, exception = ErrorCode.WORD_NOT_ACCEPTABLE)
    private List<List<String>> meanings;

    public Word toWord(long id){
        validation();
        return Word.builder()
                .wordStorageId(id)
                .words(words)
                .meanings(meanings)
                .build();
    }

    private void validation(){
        if (words.size() > 200 || meanings.size() > 200)
            throw new WordNotAcceptableException();
    }
}
