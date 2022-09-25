package com.wolves.mainproject.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.domain.dynamo.word.WordRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLike;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeMapping;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeRepository;
import com.wolves.mainproject.dto.request.my.word.storage.PostBookmarkedWordStorageDto;
import com.wolves.mainproject.dto.request.my.word.storage.RequestMyWordStorageDto;
import com.wolves.mainproject.dto.request.my.word.storage.UpdateMyWordStorageStatusDto;
import com.wolves.mainproject.dto.request.my.word.storage.UpdateWordDto;
import com.wolves.mainproject.type.StatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
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
    private DynamoDBMapperConfig config;

    @Autowired
    private DynamoDBMapper mapper;

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
     * @return : NoSQL 단어 조회 여부
     * @Author : Jangdongha
     **/
    @Test
    void getWordStorageTest(){
        // Given
        long wordStorageId = 1L;
        List<String> checkWords = Arrays.asList("apple", "banana");
        List<List<String>> checkMeanings = Arrays.asList(Arrays.asList("sup1", "sup2", "sup3"), Arrays.asList("sup3", "sup4", "sup5"));
        // When
        Word word = wordRepository.findById(wordStorageId).orElseThrow();
        List<String> words = word.getWords();
        List<List<String>> meanings = word.getMeanings();
        // Then
        assertEquals(checkWords.get(0), words.get(0));
        assertEquals(checkMeanings.get(0).get(0), meanings.get(0).get(0));
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
        wordStorage.update(dto, wordStorage);
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
     * @Description : 내 단어장 내 제목+내용 검색
     * @return : 검색한 단어를 가지고 있는 단어장 List
     * @Author : Jangdongha
     **/
    @Test
    void findWordInMyWordStorageTest(){
        // Given
        WordStorage wordStorage = wordStorageRepository.findById(1L).orElseThrow();
        User user = userRepository.findById(1L).orElseThrow();
        String searchData = "des";
        // When
        List<WordStorage> wordStoragePS = wordStorageRepository.findAllByTitleContainingOrDescriptionContainingAndUser(searchData, searchData, user);
        // Then
        assertEquals("test", wordStoragePS.get(0).getTitle());
    }

    /**
     * @Description : 좋아요 한 단어장 목록 불러오기
     * @return : 좋아요 한 단어장 목록 List
     * @Author : Jangdongha
     **/
    @Test
    void findLikeWordStorageListTest(){
        // Given
        User user = userRepository.findById(1L).orElseThrow();
        long requestId = 1L;
        WordStorage wordStorage = wordStorageRepository.findById(requestId).orElseThrow();
        WordStorageLike wordStorageLike = WordStorageLike.builder().user(user).wordStorage(wordStorage).build();
        wordStorageLikeRepository.save(wordStorageLike);
        Pageable pageable = null;
        // When
        List<WordStorageLikeMapping> wordStorageLikeMappings = wordStorageLikeRepository.findAllByUser(user, pageable).stream().toList();
        List<WordStorage> wordStorages = new ArrayList<>();
        assert wordStorageLikeMappings != null;
        wordStorageLikeMappings.forEach(mapping -> wordStorages.add(mapping.getWordStorage()));
        // Then
        assertEquals(1, wordStorages.size());
    }

    /**
     * @Description : 내 단어장 목록 불러오기
     * @return : 내 단어장 List
     * @Author : Jangdongha
     **/
    @Test
    void findMyWordStoragesTest(){
        // Given
        User user = userRepository.findById(1L).orElseThrow();
        // When
        List<WordStorage> wordStorages = wordStorageRepository.findAllByUser(user, null).stream().toList();
        // Then
        assertEquals(1, wordStorages.get(0).getId());
    }

    /**
     * @Description : 특정 단어장 단어 편집
     * @return : 단어 편집 여부
     * @Author : Jangdongha
     **/
    @Test
    void updateWordTest(){
        // Given
        long requestId = 1L;
        UpdateWordDto dto = getUpdateWordDto();
        Word updateWord = dto.toWord(requestId);
        // When
        wordRepository.save(updateWord);
        // Then
        assertEquals("a", updateWord.getWords().get(0));
        assertEquals("a뜻1", updateWord.getMeanings().get(0).get(0));
    }

    private UpdateWordDto getUpdateWordDto(){
        List<String> words = new ArrayList<>();
        List<List<String>> meanings = new ArrayList<>();
        List<String> meaning = new ArrayList<>();

        words.add("a");
        meaning.add("a뜻1"); meaning.add("a뜻2");
        meanings.add(meaning);

        return UpdateWordDto.builder()
                .words(words)
                .meanings(meanings)
                .build();
    }


}
