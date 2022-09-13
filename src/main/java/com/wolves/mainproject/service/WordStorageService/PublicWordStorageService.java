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
import com.wolves.mainproject.domain.word.storage.like.WordStorageLike;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeRepository;
import com.wolves.mainproject.dto.WordStorageDto.WordStorageType;
import com.wolves.mainproject.dto.WordStorageDto.response.WordInfoDto;
import com.wolves.mainproject.dto.WordStorageDto.response.WordStorageDetailResponseDto;
import com.wolves.mainproject.exception.category.CategoryNotFoundException;
import com.wolves.mainproject.exception.user.UserUnauthorizedException;
import com.wolves.mainproject.exception.word.WordNotFoundException;
import com.wolves.mainproject.exception.wordStorage.WordStorageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Transactional
    public List<WordStorageMapping> getPublicWordStorageOrderByLikes(int page) {
        PageRequest pageRequest = PageRequest.of(page-1,10, Sort.by(Sort.Direction.DESC,"id"));

        return wordStorageRepository.findByTypeOrderByLikeCountDesc(WordStorageType.PUBLIC.getType(), pageRequest);

    }

    @Transactional
    public List<WordStorageMapping> getPublicWordStorageByCategory(String search, int page) {
        PageRequest pageRequest = PageRequest.of(page-1,10, Sort.by(Sort.Direction.DESC,"id"));
        WordStorageCategory searchCategory = wordStorageCategoryRepository.findByName(search)
                .orElseThrow(CategoryNotFoundException::new);

        return wordStorageRepository.findByTypeAndWordStorageCategory(WordStorageType.PUBLIC.getType(), searchCategory, pageRequest);
    }

    @Transactional
    public List<WordStorageMapping> getPublicWordStorageByTitle(String search, int page) {
        PageRequest pageRequest = PageRequest.of(page-1,10, Sort.by(Sort.Direction.DESC,"id"));

        return wordStorageRepository.findByTypeAndTitleContaining(WordStorageType.PUBLIC.getType(), search, pageRequest);
    }

    @Transactional
    public WordStorageDetailResponseDto getPublicWordStorageDetails(Long id, PrincipalDetails principalDetails) {

        WordStorage wordStorage = wordStorageRepository.findByTypeAndId(WordStorageType.PUBLIC.getType(), id)
                .orElseThrow(WordStorageNotFoundException::new);

        Word wordList = wordRepository.findById(wordStorage.getId())
                .orElseThrow(WordNotFoundException::new);

        return new WordStorageDetailResponseDto(new WordInfoDto(wordList), wordStorage);
    }

    @Transactional
    public String likePublicWordStorage(Long id, PrincipalDetails principalDetails) {

        WordStorage likeWordStorage = wordStorageRepository.findByTypeAndId(WordStorageType.PUBLIC.getType(), id)
                .orElseThrow(WordStorageNotFoundException::new);
        WordStorageLike wordStorageLike = wordStorageLikeRepository.findByUserAndWordStorage(principalDetails.getUser(), likeWordStorage);

        if(wordStorageLike == null){
            likeWordStorage.update(likeWordStorage.getLikeCount() + 1);

            WordStorageLike newWordStorageLike = new WordStorageLike(principalDetails.getUser(), likeWordStorage);
            wordStorageLikeRepository.save(newWordStorageLike);

            return null;
        }

        likeWordStorage.update(likeWordStorage.getLikeCount() - 1);
        wordStorageLikeRepository.deleteByUserAndWordStorage(principalDetails.getUser(), likeWordStorage);

        return null;

    }

    @Transactional
    public List<CategoryStatisticMapping> getWordStorageStatistics() {
        return wordStorageRepository.countByCategory();
    }



}
