package com.wolves.mainproject.service.WordStorageService;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.domain.dynamo.word.WordRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageMapping;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLike;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeRepository;
import com.wolves.mainproject.dto.response.WordInfoDto;
import com.wolves.mainproject.dto.response.WordStorageDetailResponseDto;
import com.wolves.mainproject.dto.response.WordStorageResponseDto;
import com.wolves.mainproject.exception.category.CategoryNotFoundException;
import com.wolves.mainproject.exception.word.WordNotFoundException;
import com.wolves.mainproject.exception.wordStorage.WordStorageNotFoundException;
import com.wolves.mainproject.service.MyWordStorageService;
import com.wolves.mainproject.type.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficialWordStorageService {

    private final WordStorageRepository wordStorageRepository;
    private final WordStorageCategoryRepository wordStorageCategoryRepository;
    private final WordStorageLikeRepository wordStorageLikeRepository;
    private final WordRepository wordRepository;
    private final MyWordStorageService myWordStorageService;

    @Transactional
    public List<WordStorageResponseDto> getOfficialWordStoragesOrderByLike(int page,
                                                                           @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        PageRequest pageRequest = PageRequest.of(page-1,16, Sort.by(Sort.Direction.DESC,"id"));

        List<WordStorage> wordStorages = wordStorageRepository.findByStatusOrderByLikeCountDesc(StatusType.OFFICIAL, pageRequest);
        return myWordStorageService.setHaveStorageFlags(principalDetails, wordStorages);
    }


    @Transactional
    public List<WordStorageResponseDto> getOfficialWordStoragesByCategory(String category, Long lastArticleId,
                                                                          @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        PageRequest pageRequest = PageRequest.of(0,16, Sort.by(Sort.Direction.DESC,"id"));
        WordStorageCategory searchCategory = wordStorageCategoryRepository.findByName(category)
                .orElseThrow(CategoryNotFoundException::new);

        List<WordStorage> wordStorages = wordStorageRepository.findByIdLessThanAndStatusAndWordStorageCategory(lastArticleId, StatusType.OFFICIAL, searchCategory, pageRequest);
        return myWordStorageService.setHaveStorageFlags(principalDetails, wordStorages);
    }

    @Transactional
    public List<WordStorageResponseDto> getOfficialWordStoragesByTitle(String search, Long lastArticleId,
                                                                       @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        PageRequest pageRequest = PageRequest.of(0,16, Sort.by(Sort.Direction.DESC,"id"));
        List<WordStorage> wordStorages = wordStorageRepository.findByIdLessThanAndStatusAndTitleContaining(lastArticleId, StatusType.OFFICIAL, search, pageRequest);

        return myWordStorageService.setHaveStorageFlags(principalDetails, wordStorages);
    }

    @Transactional
    public WordStorageDetailResponseDto getOfficialWordStorageDetails(Long id) {
        WordStorage wordStorage = wordStorageRepository.findByStatusAndId(StatusType.OFFICIAL, id)
                .orElseThrow(WordStorageNotFoundException::new);
        Word wordList = wordRepository.findById(wordStorage.getId())
                .orElseThrow(WordNotFoundException::new);

        return new WordStorageDetailResponseDto(new WordInfoDto(wordList), wordStorage);
    }

    @Transactional
    public String likeWordStorage(Long id, PrincipalDetails principalDetails) {
        WordStorage likeWordStorage = wordStorageRepository.findById(id).orElseThrow(WordStorageNotFoundException::new);
        WordStorageLike wordStorageLike = wordStorageLikeRepository.findByUserAndWordStorage(principalDetails.getUser(), likeWordStorage);

        if(wordStorageLike == null) {
            likeWordStorage.update(likeWordStorage.getLikeCount() + 1);
            wordStorageLikeRepository.save(new WordStorageLike(principalDetails.getUser(), likeWordStorage));

            return null;
        }

        likeWordStorage.update(likeWordStorage.getLikeCount() - 1);
        wordStorageLikeRepository.deleteByUserAndWordStorage(principalDetails.getUser(), likeWordStorage);

        return null;

    }

    @Transactional
    public String copyToMyWordStorage(Long originalWordStorageId, PrincipalDetails principalDetails) {
        long copiedWordStorageId = copyWordStorage(originalWordStorageId, principalDetails);
        copyWord(originalWordStorageId, copiedWordStorageId);

        return null;
    }

    private long copyWordStorage(Long originalWordStorageId, PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
        WordStorage originalWordStorage = wordStorageRepository.findById(originalWordStorageId)
                .orElseThrow(WordStorageNotFoundException::new);
        WordStorageCategory category = wordStorageCategoryRepository.findByName(originalWordStorage.getWordStorageCategory().getName())
                .orElseThrow(CategoryNotFoundException::new);

        WordStorage copiedWordStorage = new WordStorage(user, originalWordStorage, category, StatusType.PRIVATE);

        return wordStorageRepository.save(copiedWordStorage).getId();
    }

    private void copyWord(long originalWordStorageId, long copiedWordStorageId) {
        Word word = wordRepository.findById(originalWordStorageId).orElseThrow(WordNotFoundException::new);
        word.setWordStorageId(copiedWordStorageId);

        wordRepository.save(word);
    }


}
