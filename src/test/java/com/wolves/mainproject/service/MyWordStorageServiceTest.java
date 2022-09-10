package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.domain.word.Word;
import com.wolves.mainproject.domain.word.WordMapping;
import com.wolves.mainproject.domain.word.WordRepository;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLike;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeMapping;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeRepository;
import com.wolves.mainproject.dto.request.PostBookmarkedWordStorageDto;
import com.wolves.mainproject.dto.request.RequestMyWordStorageDto;
import com.wolves.mainproject.dto.request.UpdateMyWordStorageStatusDto;
import com.wolves.mainproject.type.StatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MyWordStorageServiceTest {
    @Autowired
    private WordStorageCategoryRepository wordStorageCategoryRepository;

    @Autowired
    private WordStorageRepository wordStorageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WordStorageLikeRepository wordStorageLikeRepository;

    @Autowired
    private WordRepository wordRepository;

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

    /**
     * @Description : 특정 단어장 즐겨찾기
     * @return : 즐겨찾기 추가 or 삭제 여부
     * @Author : Jangdongha
     **/
    @Test
    void postBookmarkedWordStorageTest(){
        // Given
        long requestId = 1L;
        PostBookmarkedWordStorageDto dto = new PostBookmarkedWordStorageDto(true);
        WordStorage wordStorage = wordStorageRepository.findById(requestId).orElseThrow();
        // When
        wordStorage.update(dto);
        WordStorage wordStoragePS = wordStorageRepository.save(wordStorage);
        // Then
        assertTrue(wordStoragePS.isBookmarked());
    }

    /**
     * @Description : 내 단어장 내 단어 검색
     * @return : 검색한 단어를 가지고 있는 단어장 List
     * @Author : Jangdongha
     **/
    @Test
    void findWordInMyWordStorageTest(){
        // Given
        WordStorage wordStorage = wordStorageRepository.findById(1L).orElseThrow();
        Word word = Word.builder().word("test").description("testDescription").wordStorage(wordStorage).build();
        wordRepository.save(word);
        // When
        List<WordMapping> wordMappings = wordRepository.findAllByWord(word.getWord()).orElse(null);
        List<WordStorage> wordStorages = new ArrayList<>();
        assert wordMappings != null;
        wordMappings.forEach(wordMapping -> wordStorages.add(wordMapping.getWordStorage()));
        // Then
        assertEquals(1, wordStorages.size());
        assertEquals(wordStorage.getId(), wordStorages.get(0).getId());
    }

    @Test
    void findLikeWordStorageListTest(){
        // Given
        User user = userRepository.findById(1L).orElseThrow();
        long requestId = 1L;
        WordStorage wordStorage = wordStorageRepository.findById(requestId).orElseThrow();
        WordStorageLike wordStorageLike = WordStorageLike.builder().user(user).wordStorage(wordStorage).build();
        wordStorageLikeRepository.save(wordStorageLike);
        // When
        List<WordStorageLikeMapping> wordStorageLikeMappings = wordStorageLikeRepository.findAllByUser(user).orElse(null);
        List<WordStorage> wordStorages = new ArrayList<>();
        assert wordStorageLikeMappings != null;
        wordStorageLikeMappings.forEach(mapping -> wordStorages.add(mapping.getWordStorage()));
        // Then
        assertEquals(1, wordStorages.size());
    }


}
