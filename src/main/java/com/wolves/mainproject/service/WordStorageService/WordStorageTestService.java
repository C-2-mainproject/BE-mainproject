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
import com.wolves.mainproject.dto.request.exam.FinishWordExamRequestDto;
import com.wolves.mainproject.dto.request.exam.WordExamRequestDto;
import com.wolves.mainproject.dto.response.exam.FinishWordExamResponseDto;
import com.wolves.mainproject.exception.word.WordNotFoundException;
import com.wolves.mainproject.exception.wordStorage.WordStorageNotFoundException;
import com.wolves.mainproject.type.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WordStorageTestService {

    private final WrongAnswerRepository wrongAnswerRepository;
    private final WordRepository wordRepository;
    private final WordStorageRepository wordStorageRepository;
    private final WordStorageWrongAnswerRepository wordStorageWrongAnswerRepository;

    @Transactional
    public Word createWordExam(WordExamRequestDto wordExamDto) {

        return wordRepository.findById(wordExamDto.getWordStorageId())
                .orElseThrow(WordNotFoundException::new);
    }


    @Transactional
    public FinishWordExamResponseDto finishWordExam(FinishWordExamRequestDto finishWordExamDto, PrincipalDetails principalDetails) {

        WrongAnswer wrongAnswer = saveAndGetWrongAnswer(finishWordExamDto, principalDetails);
        WordStorage wordStorage = saveAndGetWordStorage(finishWordExamDto, principalDetails);
        saveWordStorageWrongAnswer(wrongAnswer, wordStorage);

        Long newWordStorageId = saveWordAndGetStorageId(finishWordExamDto, wordStorage);
        updateLastTestTime(finishWordExamDto.getWordStorageId());

        return new FinishWordExamResponseDto(newWordStorageId);
    }

    private void updateLastTestTime(Long wordStorageId) {
        WordStorage originalWordStorage = wordStorageRepository.
                findById(wordStorageId).orElseThrow(WordStorageNotFoundException::new);

        originalWordStorage.setLastTestAt(LocalDateTime.now());
        wordStorageRepository.save(originalWordStorage);
    }

    Long saveWordAndGetStorageId(FinishWordExamRequestDto finishWordExamDto, WordStorage newWordStorage) {
        Word word = finishWordExamDto.toWord(newWordStorage, finishWordExamDto);
        return wordRepository.save(word).getWordStorageId();
    }

    void saveWordStorageWrongAnswer(WrongAnswer wrongAnswer, WordStorage newWordStorage) {
        WordStorageWrongAnswer wordStorageWrongAnswer = WordStorageWrongAnswer.builder()
                .wrongAnswer(wrongAnswer)
                .wordStorage(newWordStorage)
                .build();

        wordStorageWrongAnswerRepository.save(wordStorageWrongAnswer);
    }

    WordStorage saveAndGetWordStorage(FinishWordExamRequestDto finishWordExamDto, PrincipalDetails principalDetails) {
        WordStorageCategory wordStorageCategory = wordStorageRepository.findById(finishWordExamDto.getWordStorageId())
                .orElseThrow(WordStorageNotFoundException::new).getWordStorageCategory();

        String newWordStorageTitle = finishWordExamDto.getWordStorageId() + "번 단어장의 오답노트";
        WordStorage newWordStorage = finishWordExamDto.toWordStorage(newWordStorageTitle, wordStorageCategory, principalDetails);

        wordStorageRepository.save(newWordStorage);
        return newWordStorage;
    }

    WrongAnswer saveAndGetWrongAnswer(FinishWordExamRequestDto finishWordExamDto, PrincipalDetails principalDetails) {
        WrongAnswer wrongAnswer = finishWordExamDto.toAnswer(finishWordExamDto, principalDetails);
        wrongAnswerRepository.save(wrongAnswer);
        return wrongAnswer;
    }

    @Transactional
    public List<WrongAnswerMapping> getExamHistory(PrincipalDetails principalDetails) {

        return wrongAnswerRepository.customFindALlByUser(principalDetails.getUser());
    }


}
