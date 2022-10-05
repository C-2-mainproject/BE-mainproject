package com.wolves.mainproject.dto.request.exam;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.wrong.answer.WrongAnswer;
import com.wolves.mainproject.dto.response.WordInfoDto;
import com.wolves.mainproject.type.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FinishWordExamRequestDto {
    Long wordStorageId;
    String testType;
    int totalWords;
    int wrongWords;
    Long time;
    WordInfoDto collectionWrongWord;

    public WrongAnswer toAnswer(FinishWordExamRequestDto finishWordExamDto, PrincipalDetails principalDetails){
        return WrongAnswer.builder()
                .testType(finishWordExamDto.getTestType())
                .totalWords(finishWordExamDto.getTotalWords())
                .wrongWords(finishWordExamDto.getWrongWords())
                .time(finishWordExamDto.getTime())
                .user(principalDetails.getUser())
                .build();
    }


    public WordStorage toWordStorage(String newTitle, WordStorageCategory wordStorageCategory, PrincipalDetails principalDetails) {

        return WordStorage.builder()
                .wordStorageCategory(wordStorageCategory)
                .title(newTitle)
                .description(newTitle)
                .status(StatusType.PRIVATE)
                .user(principalDetails.getUser())
                .likeCount(0)
                .build();
    }

    public Word toWord(WordStorage newWordStorage, FinishWordExamRequestDto finishWordExamDto){

        return Word.builder()
                .wordStorageId(newWordStorage.getId())
                .words(finishWordExamDto.getCollectionWrongWord().getWord())
                .meanings(finishWordExamDto.getCollectionWrongWord().getMeaning())
                .build();

    }
}
