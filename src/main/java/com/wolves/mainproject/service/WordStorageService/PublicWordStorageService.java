package com.wolves.mainproject.service.WordStorageService;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.domain.dynamo.word.WordRepository;
import com.wolves.mainproject.domain.word.storage.category.CategoryStatisticMapping;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageMapping;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
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
public class PublicWordStorageService {

    private final WordStorageRepository wordStorageRepository;
    private final WordStorageCategoryRepository wordStorageCategoryRepository;
    private final WordStorageLikeRepository wordStorageLikeRepository;
    private final WordRepository wordRepository;
    private final MyWordStorageService wordStorageService;

    @Transactional
    public List<WordStorageResponseDto> getPublicWordStoragesOrderByLikes(int page, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        PageRequest pageRequest = PageRequest.of(page-1,10, Sort.by(Sort.Direction.DESC,"id"));
        List<WordStorage> wordStorages = wordStorageRepository.findByStatusOrderByLikeCountDesc(StatusType.PUBLIC, pageRequest);

        return wordStorageService.setHaveStorageFlags(principalDetails, wordStorages);

    }

    @Transactional
    public List<WordStorageResponseDto> getPublicWordStoragesByCategory(String search, Long lastArticleId,
                                                                        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        PageRequest pageRequest = PageRequest.of(0,16, Sort.by(Sort.Direction.DESC,"id"));
        WordStorageCategory searchCategory = wordStorageCategoryRepository.findByName(search)
                .orElseThrow(CategoryNotFoundException::new);

        List<WordStorage> wordStorages = wordStorageRepository.findByIdLessThanAndStatusAndWordStorageCategory(lastArticleId, StatusType.PUBLIC, searchCategory, pageRequest);

        return wordStorageService.setHaveStorageFlags(principalDetails, wordStorages);
    }

    @Transactional
    public List<WordStorageResponseDto> getPublicWordStoragesByTitle(String search, Long lastArticleId,
                                                                     @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        PageRequest pageRequest = PageRequest.of(0,16, Sort.by(Sort.Direction.DESC,"id"));
        List<WordStorage> wordStorages = wordStorageRepository.findByIdLessThanAndStatusAndTitleContaining(lastArticleId, StatusType.PUBLIC, search, pageRequest);

        return wordStorageService.setHaveStorageFlags(principalDetails, wordStorages);
    }

    @Transactional
    public WordStorageDetailResponseDto getPublicWordStorageDetails(Long id) {

        WordStorage wordStorage = wordStorageRepository.findByStatusAndId(StatusType.PUBLIC, id)
                .orElseThrow(WordStorageNotFoundException::new);

        Word wordList = wordRepository.findById(wordStorage.getId())
                .orElseThrow(WordNotFoundException::new);

        return new WordStorageDetailResponseDto(new WordInfoDto(wordList), wordStorage);
    }

    @Transactional
    public List<CategoryStatisticMapping> getWordStorageStatistics() {
        return wordStorageRepository.countByCategory();
    }



}
