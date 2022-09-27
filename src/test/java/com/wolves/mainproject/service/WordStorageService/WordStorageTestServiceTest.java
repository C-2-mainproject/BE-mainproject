package com.wolves.mainproject.service.WordStorageService;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.domain.dynamo.word.WordRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.answer.WrongAnswerMapping;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
import com.wolves.mainproject.domain.wrong.answer.WrongAnswer;
import com.wolves.mainproject.domain.wrong.answer.WrongAnswerRepository;
import com.wolves.mainproject.dto.request.exam.FinishWordExamRequestDto;
import com.wolves.mainproject.dto.response.WordInfoDto;
import com.wolves.mainproject.exception.word.WordNotFoundException;
import com.wolves.mainproject.type.StatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WordStorageTestServiceTest {

    @Autowired
    public WordStorageRepository wordStorageRepository;
    @Autowired
    public WordStorageCategoryRepository wordStorageCategoryRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public WordRepository wordRepository;
    @Autowired
    public WrongAnswerRepository wrongAnswerRepository;
    @Autowired
    public WordStorageTestService wordStorageTestService;

    void makeWordStorage(){
        User user = userRepository.findById(1L).orElseThrow();
        WordStorageCategory category = wordStorageCategoryRepository.findById(1L).orElseThrow();
        WordStorage wordStorage = WordStorage.builder()
                .title("test")
                .wordStorageCategory(category)
                .description("testDescription")
                .status(StatusType.PUBLIC)
                .user(user)
                .build();

        wordStorageRepository.save(wordStorage);
    }

    @BeforeEach
    void makeWordStorages(){
        makeWordStorage();
        makeWordStorage();
        makeWordStorage();
    }

    @Test
    @Order(1)
    void createWordExam() {
        // given
        Long storageId = 1L;
        List<List<String>> meaning = Arrays.asList(Arrays.asList("apple1", "apple2", "apple3"), Arrays.asList("banana1", "banana2", "banana3"));
        List<String> word = Arrays.asList("apple", "banana");

        Word words = new Word(storageId, word, meaning);
        makeWordStorages();

        // when
        wordRepository.save(words);
        Word savedWord = wordRepository.findById(storageId).orElseThrow();

        // then
        assertNotNull(savedWord);
        System.out.println(savedWord);
    }

    @Test
    @Order(2)
    void finishWordExam() {
        // given
        User user = userRepository.findById(1L).orElseThrow();
        PrincipalDetails principalDetails = new PrincipalDetails();
        principalDetails.setUser(user);

        List<List<String>> meaning = Arrays.asList(Arrays.asList("apple1", "apple2", "apple3"), Arrays.asList("banana1", "banana2", "banana3"));
        List<String> word = Arrays.asList("apple", "banana");

        FinishWordExamRequestDto finishWordExamDto = FinishWordExamRequestDto.builder()
                .wordStorageId(1L)
                .testType("스펠링")
                .totalWords(3)
                .wrongWords(1)
                .time(30L)
                .collectionWrongWord(WordInfoDto.builder()
                        .meaning(meaning)
                        .word(word)
                        .build())
                .build();

        // when
        WrongAnswer wrongAnswer = wordStorageTestService.saveAndGetWrongAnswer(finishWordExamDto, principalDetails);
        WordStorage wordStorage = wordStorageTestService.saveAndGetWordStorage(finishWordExamDto, principalDetails);
        wordStorageTestService.saveWordStorageWrongAnswer(wrongAnswer, wordStorage);

        Long newWordStorageId = wordStorageTestService.saveWordAndGetStorageId(finishWordExamDto, wordStorage);

        // then
        assertEquals(finishWordExamDto.getWrongWords(), wrongAnswer.getWrongWords());
        assertEquals(finishWordExamDto.getTotalWords(), wrongAnswer.getTotalWords());
        assertEquals(finishWordExamDto.getTestType(), wrongAnswer.getTestType());
        assertEquals(StatusType.PRIVATE,wordStorage.getStatus());
        assertNotNull(newWordStorageId);
    }

    @Test
    @Order(3)
    void getExamHistory() {
        // given
        User user = userRepository.findById(1L).orElseThrow();

        // when
        finishWordExam();
        List<WrongAnswerMapping> wrongAnswers = wrongAnswerRepository.customFindALlByUser(user);

        // then
        assertNotNull(wrongAnswers);

    }
}