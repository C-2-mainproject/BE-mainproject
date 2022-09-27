package com.wolves.mainproject.service.WordStorageService;

import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.domain.dynamo.word.WordRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLike;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeRepository;
import com.wolves.mainproject.dto.request.my.word.storage.UpdateWordDto;
import com.wolves.mainproject.dto.response.WordInfoDto;
import com.wolves.mainproject.dto.response.WordStorageDetailResponseDto;
import com.wolves.mainproject.exception.word.WordNotFoundException;
import com.wolves.mainproject.exception.wordStorage.WordStorageNotFoundException;
import com.wolves.mainproject.service.MyWordStorageService;
import com.wolves.mainproject.type.StatusType;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OfficialWordStorageServiceTest {

    @Autowired
    public WordStorageRepository wordStorageRepository;
    @Autowired
    public WordStorageCategoryRepository wordStorageCategoryRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public WordRepository wordRepository;
    @Autowired
    public WordStorageLikeRepository wordStorageLikeRepository;

    void makeWordStorage(){
        User user = userRepository.findById(1L).orElseThrow();
        WordStorageCategory category = wordStorageCategoryRepository.findById(1L).orElseThrow();
        WordStorage wordStorage = WordStorage.builder()
                .title("test")
                .wordStorageCategory(category)
                .description("testDescription")
                .status(StatusType.OFFICIAL)
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
    void getOfficialWordStoragesOrderByLike() {
        //given
        int page = 1;
        long originalStoragesSize = wordStorageRepository.countByStatus(StatusType.OFFICIAL);

        //when
        makeWordStorages();
        PageRequest pageRequest = PageRequest.of(page-1,16, Sort.by(Sort.Direction.DESC,"id"));
        List<WordStorage> wordStorages = wordStorageRepository.findByStatusOrderByLikeCountDesc(StatusType.OFFICIAL, pageRequest);


        //then
        assertEquals(originalStoragesSize+3, wordStorages.size());
        assertEquals(StatusType.OFFICIAL, wordStorages.get(0).getStatus());
        assertEquals("test",wordStorages.get(0).getTitle());
        assertEquals("testDescription",wordStorages.get(0).getDescription());

    }

    @Test
    @Order(2)
    void getOfficialWordStoragesByCategory() {
        //given
        int page = 1;
        long lastArticleId = 999L;
        long originalStoragesSize = wordStorageRepository.countByStatus(StatusType.OFFICIAL);

        //when
        makeWordStorages();
        PageRequest pageRequest = PageRequest.of(page-1,16, Sort.by(Sort.Direction.DESC,"id"));
        WordStorageCategory category = wordStorageCategoryRepository.findById(1L).orElseThrow();
        List<WordStorage> wordStorages = wordStorageRepository.findByIdLessThanAndStatusAndWordStorageCategory(lastArticleId, StatusType.OFFICIAL, category, pageRequest);

        //then
        assertEquals(originalStoragesSize+3, wordStorages.size());
        assertEquals(StatusType.OFFICIAL, wordStorages.get(0).getStatus());
        assertEquals(category.getName(), wordStorages.get(1).getWordStorageCategory().getName());
    }

    @Test
    @Order(3)
    void getOfficialWordStoragesByTitle() {
        //given
        long lastArticleId = 999L;
        long originalStoragesSize = wordStorageRepository.countByStatus(StatusType.OFFICIAL);
        String search = "es";

        //when
        makeWordStorages();
        PageRequest pageRequest = PageRequest.of(0,16, Sort.by(Sort.Direction.DESC,"id"));
        List<WordStorage> wordStorages = wordStorageRepository.findByIdLessThanAndStatusAndTitleContaining(lastArticleId, StatusType.OFFICIAL, search, pageRequest);

        //then
        assertEquals(originalStoragesSize+3, wordStorages.size());
        assertEquals(StatusType.OFFICIAL, wordStorages.get(0).getStatus());
        assertEquals("test", wordStorages.get(0).getTitle());
    }

    @Test
    @Order(4)
    void getOfficialWordStorageDetails() {
        //given
        List<List<String>> meaning = Arrays.asList(Arrays.asList("apple1", "apple2", "apple3"), Arrays.asList("banana1", "banana2", "banana3"));
        List<String> word = Arrays.asList("apple", "banana");

        Word words = new Word(1L, word, meaning);
        wordRepository.save(words);

        //when
        makeWordStorages();
        WordStorage wordStorage = wordStorageRepository.findByStatusAndId(StatusType.OFFICIAL, 1L).orElseThrow();
        Word wordList = wordRepository.findById(wordStorage.getId()).orElseThrow();
        WordStorageDetailResponseDto dto = new WordStorageDetailResponseDto(new WordInfoDto(wordList), wordStorage);

        //then
        assertEquals(words.getWords(), dto.getWords().getWord());
        assertEquals(words.getWordStorageId(), wordStorage.getId());
        assertEquals(words.getMeanings(), dto.getWords().getMeaning());

    }

    @Test
    @Order(5)
    void likeWordStorage() {
        //given
        long id = 1L;
        User user = userRepository.findById(1L).orElseThrow();
        makeWordStorages();
        WordStorage likeWordStorage = wordStorageRepository.findById(id).orElseThrow();
        WordStorageLike wordStorageLike = wordStorageLikeRepository.findByUserAndWordStorage(user, likeWordStorage);
        WordStorageLike tempLike = null;


        //when
        if(wordStorageLike == null) {
            likeWordStorage.update(likeWordStorage.getLikeCount() + 1);
            tempLike = wordStorageLikeRepository.save(new WordStorageLike(user, likeWordStorage));
        } else {
            likeWordStorage.update(likeWordStorage.getLikeCount() - 1);
            wordStorageLikeRepository.deleteByUserAndWordStorage(user, likeWordStorage);
        }

        //then
        if(wordStorageLike == null){
            assertNotNull(tempLike);
        } else {
            assertNull(tempLike);
        }


    }




}