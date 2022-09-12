package com.wolves.mainproject.service;


import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.domain.dynamo.word.WordRepository;
import com.wolves.mainproject.domain.game.history.GameHistory;
import com.wolves.mainproject.domain.game.history.GameHistoryRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.dto.response.GameHistoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class GameHistoryServiceTest {
    private @Autowired WordRepository wordRepository;



    @Test
    void createComment_ValidInput_CreatedComment() {
        // given
        List<List<String>> meaning = Arrays.asList(Arrays.asList("sup1", "sup2", "sup3"), Arrays.asList("sup3", "sup4", "sup5"));
        List<String> word = Arrays.asList("apple", "banana");

        Word testDynamo = new Word(1L, word, meaning);
        wordRepository.save(testDynamo);

        System.out.println(wordRepository.findById(1L).orElseThrow());
    }

}
