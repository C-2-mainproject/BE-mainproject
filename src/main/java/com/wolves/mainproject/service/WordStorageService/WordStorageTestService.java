package com.wolves.mainproject.service.WordStorageService;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.domain.dynamo.word.WordRepository;
import com.wolves.mainproject.domain.word.storage.answer.WrongAnswerMapping;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.answer.WordStorageWrongAnswer;
import com.wolves.mainproject.domain.word.storage.answer.WordStorageWrongAnswerRepository;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.wrong.answer.WrongAnswer;
import com.wolves.mainproject.domain.wrong.answer.WrongAnswerRepository;
import com.wolves.mainproject.dto.WordStorageDto.WordStorageType;
import com.wolves.mainproject.dto.WordStorageDto.requset.FinishWordExamRequestDto;
import com.wolves.mainproject.dto.WordStorageDto.requset.WordExamRequestDto;
import com.wolves.mainproject.exception.user.UserUnauthorizedException;
import com.wolves.mainproject.exception.word.WordNotFoundException;
import com.wolves.mainproject.exception.wordStorage.WordStorageNotFoundException;
import com.wolves.mainproject.type.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class WordStorageTestService {

    private final WrongAnswerRepository wrongAnswerRepository;
    private final WordRepository wordRepository;
    private final WordStorageRepository wordStorageRepository;
    private final WordStorageWrongAnswerRepository wordStorageWrongAnswerRepository;

    @Transactional
    public Word createWordExam(WordExamRequestDto wordExamDto, PrincipalDetails principalDetails) {

        checkLoginStatus(principalDetails);

        return wordRepository.findById(wordExamDto.getWordStorageId())
                .orElseThrow(WordNotFoundException::new);
    }


    @Transactional
    public String finishWordExam(FinishWordExamRequestDto finishWordExamDto, PrincipalDetails principalDetails) {
        checkLoginStatus(principalDetails);

        // 1. wrong answer 테이블에 저장
        WrongAnswer wrongAnswer = WrongAnswer.builder()
                .testType(finishWordExamDto.getTestType())
                .totalWords(finishWordExamDto.getTotalWords())
                .wrongWords(finishWordExamDto.getWrongWords())
                .time(finishWordExamDto.getTime())
                .user(principalDetails.getUser())
                .build();

        wrongAnswerRepository.save(wrongAnswer);

        // 2. word storage 테이블에 저장

        // 기존 wordStorageCategory 확인을 위한 조회
        WordStorageCategory wordStorageCategory = wordStorageRepository.findById(finishWordExamDto.getWordStorageId())
                .orElseThrow(WordStorageNotFoundException::new).getWordStorageCategory();

        // 새 wordStorage 저장
        WordStorage newWordStorage = WordStorage.builder()
                .wordStorageCategory(wordStorageCategory)
                .title("오답노트")
                .description("오답노트")
                .status(StatusType.PRIVATE)
                .user(principalDetails.getUser())
                .likeCount(0)
                .type(WordStorageType.PRIVATE.getType())
                .build();

        wordStorageRepository.save(newWordStorage);

        // 3. wrong storage wrong answer 테이블에 저장
        WordStorageWrongAnswer wordStorageWrongAnswer = WordStorageWrongAnswer.builder()
                .wrongAnswer(wrongAnswer)
                .wordStorage(newWordStorage)
                .build();

        wordStorageWrongAnswerRepository.save(wordStorageWrongAnswer);


        // 4. Word 테이블에 저장
        Word word = Word.builder()
                .wordStorageId(newWordStorage.getId())
                .words(finishWordExamDto.getCollectionWrongWord().getWord())
                .meanings(finishWordExamDto.getCollectionWrongWord().getMeaning())
                        .build();

        wordRepository.save(word);


        return null;
    }

    @Transactional
    public List<WrongAnswerMapping> getExamHistory(PrincipalDetails principalDetails) {
        checkLoginStatus(principalDetails);

        return wrongAnswerRepository.customFindALl();
    }


    public void checkLoginStatus(PrincipalDetails principalDetails){
        if(principalDetails.getUser() == null){
            throw new UserUnauthorizedException();
        }

    }
}
