package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
import com.wolves.mainproject.dto.request.RequestMyWordStorageDto;
import com.wolves.mainproject.dto.request.UpdateMyWordStorageStatusDto;
import com.wolves.mainproject.type.StatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MyWordStorageServiceTest {
    @Autowired
    private WordStorageCategoryRepository wordStorageCategoryRepository;

    @Autowired
    private WordStorageRepository wordStorageRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void makeWordStorage(){
        User user = userRepository.findById(1L).orElseThrow();
        WordStorageCategory category = wordStorageCategoryRepository.findById(1L).orElseThrow();
        WordStorage wordStorage = WordStorage.builder()
                .title("test")
                .wordStorageCategory(category)
                .description("testDescription")
                .status(StatusType.PRIVATE)
                .user(user)
                .build();

        wordStorageRepository.save(wordStorage);
    }

    /**
     * @Description : 특정 단어장 수정
     * @return : 변경 여부
     * @Author : Jangdongha
     **/
    @Test
    void updateWordStorageTest(){
        // Given
        WordStorage wordStorage = wordStorageRepository.findById(1L).orElseThrow();
        RequestMyWordStorageDto dto = RequestMyWordStorageDto.builder()
                .title("updateTest")
                .category("토익")
                .description("updateTest")
                .status(true)
                .build();
        WordStorageCategory category = wordStorageCategoryRepository.findByName("토익").orElseThrow();
        // When
        wordStorage.update(dto, category);
        WordStorage wordStoragePS = wordStorageRepository.save(wordStorage);
        // Then
        assertEquals("updateTest", wordStoragePS.getTitle());
    }

    /**
     * @Description : 특정 단어장 삭제
     * @return : 삭제 여부
     * @Author : Jangdongha
     **/
    @Test
    void deleteWordStorageTest(){
        // Given
        long deleteId = 1L;
        WordStorage wordStorage = wordStorageRepository.findById(deleteId).orElseThrow();
        // When
        wordStorageRepository.delete(wordStorage);
        // Then
        assertNull(wordStorageRepository.findById(deleteId).orElse(null));
    }

    /**
     * @Description : 특정 단어장 조회
     * @return : 조회 여부
     * @Author : Jangdongha
     **/
    @Test
    void getWordStorageTest(){
        // Given
        long requestId = 1L;
        // When
        WordStorage wordStoragePS = wordStorageRepository.findById(requestId).orElse(null);
        // Then
        assertNotNull(wordStoragePS);
    }

    /**
     * @Description : 특정 단어장 public / private 변경
     * @return : 변경 여부
     * @Author : Jangdongha
     **/
    @Test
    void updateWordStorageStatusTest(){
        // Given
        UpdateMyWordStorageStatusDto dto = new UpdateMyWordStorageStatusDto(true);
        long requestId = 1L;
        WordStorage wordStorage = wordStorageRepository.findById(requestId).orElseThrow();
        // When
        wordStorage.update(dto);
        WordStorage wordStoragePS = wordStorageRepository.save(wordStorage);
        // Then
        assertEquals(StatusType.PUBLIC, wordStoragePS.getStatus());
    }
}
