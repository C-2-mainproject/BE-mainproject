package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.domain.dynamo.word.WordRepository;
import com.wolves.mainproject.domain.game.history.GameHistory;
import com.wolves.mainproject.domain.game.history.GameHistoryRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.WordStorageMapping;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.dto.request.game.history.PostGameHistoryDto;
import com.wolves.mainproject.dto.response.GameHistoryDto;
import com.wolves.mainproject.dto.response.RankingByGameHistoryDto;
import com.wolves.mainproject.dto.response.TicketDto;
import com.wolves.mainproject.dto.response.WordDto;
import com.wolves.mainproject.exception.game.history.HistoryNotFoundException;
import com.wolves.mainproject.exception.word.WordNotFoundException;
import com.wolves.mainproject.type.StatusType;
import com.wolves.mainproject.util.AES256;
import com.wolves.mainproject.util.UUIDTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {
    private final GameHistoryRepository gameHistoryRepository;
    private final WordStorageRepository wordStorageRepository;
    private final WordRepository wordRepository;

    @Transactional(readOnly = true)
    public GameHistoryDto findMyRecord(User user){
        return new GameHistoryDto(gameHistoryRepository.findByUser(user).orElseThrow(HistoryNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public List<RankingByGameHistoryDto> findRanking(){
        List<GameHistory> gameHistories = gameHistoryRepository.findAllByRanking();
        return gameHistories.stream().map(RankingByGameHistoryDto::new).toList();
    }

    @Transactional
    public void postGameResult(User user, PostGameHistoryDto dto){
        GameHistory gameHistory = gameHistoryRepository.findByUser(user).orElseThrow(HistoryNotFoundException::new);
        gameHistory.update(dto);
    }

    @Transactional(readOnly = true)
    public WordDto getWordFromRoomId(String roomId){
        UUIDTransformer uuidTransformer = new UUIDTransformer(roomId);
        List<WordStorageMapping> officialWordStorages = wordStorageRepository.findByStatus(StatusType.OFFICIAL);
        long wordStorageId = officialWordStorages.get(uuidTransformer.uuidToAbsIntInRange(officialWordStorages.size())).getId();
        Word word = wordRepository.findById(wordStorageId).orElseThrow(WordNotFoundException::new);
        return new WordDto(word.getWords(), word.getMeanings());
    }

    public TicketDto certificationGameUser(HttpServletRequest request){
        String encryptCookie = new AES256().encrypt(request.getCookies()[0].getValue());
        return new TicketDto(encryptCookie);
    }
}
