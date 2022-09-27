package com.wolves.mainproject.service.WordStorageService;

import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.domain.dynamo.word.WordRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.category.CategoryStatisticMapping;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeRepository;
import com.wolves.mainproject.dto.response.WordInfoDto;
import com.wolves.mainproject.dto.response.WordStorageDetailResponseDto;
import com.wolves.mainproject.type.StatusType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PublicWordStorageServiceTest {

    @Autowired
    public WordStorageRepository wordStorageRepository;
    @Autowired
    public WordStorageCategoryRepository wordStorageCategoryRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public WordRepository wordRepository;

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

    void makeWordStorages(){
        makeWordStorage();
        makeWordStorage();
        makeWordStorage();
    }

    @Test
    @Order(1)
    void getPublicWordStoragesOrderByLikes() {
        //given
        int page = 1;
        long originalStoragesSize = wordStorageRepository.countByStatus(StatusType.PUBLIC);
        makeWordStorages();
        PageRequest pageRequest = PageRequest.of(page-1,16, Sort.by(Sort.Direction.DESC,"id"));

        //when
        List<WordStorage> wordStorages = wordStorageRepository.findByStatusOrderByLikeCountDesc(StatusType.PUBLIC, pageRequest);


        //then
        assertEquals(originalStoragesSize+3, wordStorages.size());
        assertEquals(StatusType.PUBLIC, wordStorages.get(0).getStatus());
        assertEquals("test",wordStorages.get(0).getTitle());
        assertEquals("testDescription",wordStorages.get(0).getDescription());
    }

    @Test
    @Order(2)
    void getPublicWordStoragesByCategory() {
        //given
        int page = 1;
        long lastArticleId = 999L;
        long originalStoragesSize = wordStorageRepository.countByStatus(StatusType.PUBLIC);
        makeWordStorages();
        PageRequest pageRequest = PageRequest.of(page-1,16, Sort.by(Sort.Direction.DESC,"id"));

        //when
        WordStorageCategory category = wordStorageCategoryRepository.findById(1L).orElseThrow();
        List<WordStorage> wordStorages = wordStorageRepository.findByIdLessThanAndStatusAndWordStorageCategory(lastArticleId, StatusType.PUBLIC, category, pageRequest);

        //then
        assertEquals(originalStoragesSize+3, wordStorages.size());
        assertEquals(StatusType.PUBLIC, wordStorages.get(0).getStatus());
        assertEquals(category.getName(), wordStorages.get(1).getWordStorageCategory().getName());
    }

    @Test
    @Order(3)
    void getPublicWordStoragesByTitle() {
        //given
        long lastArticleId = 999L;
        long originalStoragesSize = wordStorageRepository.countByStatus(StatusType.PUBLIC);
        String search = "es";
        makeWordStorages();
        PageRequest pageRequest = PageRequest.of(0,16, Sort.by(Sort.Direction.DESC,"id"));

        //when
        List<WordStorage> wordStorages = wordStorageRepository.findByIdLessThanAndStatusAndTitleContaining(lastArticleId, StatusType.PUBLIC, search, pageRequest);

        //then
        assertEquals(originalStoragesSize+3, wordStorages.size());
        assertEquals(StatusType.PUBLIC, wordStorages.get(0).getStatus());
        assertEquals("test", wordStorages.get(0).getTitle());
    }

    @Test
    @Order(4)
    void getPublicWordStorageDetails() {
        //given
        List<List<String>> meaning = Arrays.asList(Arrays.asList("apple1", "apple2", "apple3"), Arrays.asList("banana1", "banana2", "banana3"));
        List<String> word = Arrays.asList("apple", "banana");

        Word words = new Word(1L, word, meaning);
        makeWordStorages();

        //when
        wordRepository.save(words);
        WordStorage wordStorage = wordStorageRepository.findByStatusAndId(StatusType.PUBLIC, 1L).orElseThrow();
        Word wordList = wordRepository.findById(wordStorage.getId()).orElseThrow();
        WordStorageDetailResponseDto dto = new WordStorageDetailResponseDto(new WordInfoDto(wordList), wordStorage);

        //then
        assertEquals(words.getWords(), dto.getWords().getWord());
        assertEquals(words.getWordStorageId(), wordStorage.getId());
        assertEquals(words.getMeanings(), dto.getWords().getMeaning());
    }

    @Test
    @Order(5)
    void getWordStorageStatistics() {

        // given
        makeWordStorages();

        // when
        List<CategoryStatisticMapping> statisticInfo = wordStorageRepository.countByCategory();
        WordStorageCategory category = wordStorageCategoryRepository.findByName(statisticInfo.get(0).getCategoryName()).orElseThrow();

        // then
        assertNotNull(statisticInfo);
        assertNotNull(category);
    }
}