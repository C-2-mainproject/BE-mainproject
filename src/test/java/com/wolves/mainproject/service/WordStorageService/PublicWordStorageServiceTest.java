package com.wolves.mainproject.service.WordStorageService;

import com.wolves.mainproject.config.auth.PrincipalDetails;
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
import com.wolves.mainproject.dto.response.WordStorageResponseDto;
import com.wolves.mainproject.type.StatusType;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    public PublicWordStorageService publicWordStorageService;

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
    void getPublicWordStoragesOrderByLikes() {
        //given
        int page = 1;
        PrincipalDetails principalDetails = new PrincipalDetails();
        principalDetails.setUser(userRepository.findById(1L).orElseThrow());

        //when
        List<WordStorageResponseDto> wordStorageResponseDto = publicWordStorageService.getPublicWordStoragesOrderByLikes(page, principalDetails);

        //then
        assertNotNull(wordStorageResponseDto);
        assertTrue(wordStorageResponseDto.size() <= 16);
        assertEquals("test", wordStorageResponseDto.get(0).getTitle());
        assertEquals("testDescription", wordStorageResponseDto.get(0).getDescription());
        assertFalse(wordStorageResponseDto.get(0).isHaveStorage());
    }

    @Test
    @Order(2)
    void getPublicWordStoragesByCategory() {
        //given
        String category = wordStorageCategoryRepository.findById(1L).orElseThrow().getName();
        long lastArticleId = 999L;
        PrincipalDetails principalDetails = new PrincipalDetails();
        principalDetails.setUser(userRepository.findById(1L).orElseThrow());

        //when
        List<WordStorageResponseDto> wordStorageResponseDto = publicWordStorageService.getPublicWordStoragesByCategory(category, lastArticleId, principalDetails);
        WordStorage wordStorage = wordStorageRepository.findById(wordStorageResponseDto.get(0).getId()).orElseThrow();

        //then
        assertNotNull(wordStorageResponseDto);
        assertTrue(wordStorageResponseDto.size() <= 16);
        assertFalse(wordStorageResponseDto.get(0).isHaveStorage());
        assertEquals(wordStorage.getWordStorageCategory().getName(), category);
    }

    @Test
    @Order(3)
    void getPublicWordStoragesByTitle() {
        //given
        long lastArticleId = 999L;
        PrincipalDetails principalDetails = new PrincipalDetails();
        principalDetails.setUser(userRepository.findById(1L).orElseThrow());
        String search = "test";

        //when
        List<WordStorageResponseDto> wordStorageResponseDto = publicWordStorageService.getPublicWordStoragesByTitle(search, lastArticleId, principalDetails);

        //then
        assertNotNull(wordStorageResponseDto);
        assertTrue(wordStorageResponseDto.size() <= 16);
        assertFalse(wordStorageResponseDto.get(0).isHaveStorage());
        assertEquals(search, wordStorageResponseDto.get(0).getTitle());
    }

    @Test
    @Order(4)
    void getPublicWordStorageDetails() {
        //given
        List<List<String>> meaning = Arrays.asList(Arrays.asList("apple1", "apple2", "apple3"), Arrays.asList("banana1", "banana2", "banana3"));
        List<String> word = Arrays.asList("apple", "banana");
        Word words = new Word(1L, word, meaning);

        Long id = 1L;

        //when
        wordRepository.save(words);
        WordStorageDetailResponseDto responseDto = publicWordStorageService.getPublicWordStorageDetails(id);

        //then
        assertEquals(words.getWords(), responseDto.getWords().getWord());
        assertEquals(words.getMeanings(), responseDto.getWords().getMeaning());
        assertEquals(id, responseDto.getId());
    }

    @Test
    @Order(5)
    void getWordStorageStatistics() {

        // when
        List<CategoryStatisticMapping> statistics = publicWordStorageService.getWordStorageStatistics();

        // then
        assertNotNull(statistics);
        assertNotNull(statistics.get(0).getCategoryName());
        assertNotNull(statistics.get(0).getCount());
    }
}