package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageMapping;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.type.StatusType;
import com.wolves.mainproject.util.UUIDTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private WordStorageRepository wordStorageRepository;

    /**
     * @Returns : wordStorageId
     * @Description : UUID 값을 통한 일정 범위 내 long value. 같은 RoomId 내 같은 공인단어장을 return 해주기 위함
     * @Author : 장동하
     **/
    @Test
    public void getOfficialWordStorageForGameTest(){
        String uuidString = "28ea88ba-7d73-472b-8625-de2a1e6f77a2";
        UUIDTransformer uuidTransformer = new UUIDTransformer(uuidString);


        List<WordStorageMapping> list = wordStorageRepository.findByStatus(StatusType.OFFICIAL);
        long officialSize = list.size();


        officialSize = 23; // 테스트 시에는 공인 단어장이 1개도 없으므로 size 를 임의로 넣었음
        long wordStorageId = uuidTransformer.uuidToAbsLongInRange(officialSize);
        System.out.println(wordStorageId);
    }
}
