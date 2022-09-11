package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeMapping;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeRepository;
import com.wolves.mainproject.dto.request.PostBookmarkedWordStorageDto;
import com.wolves.mainproject.dto.request.RequestMyWordStorageDto;
import com.wolves.mainproject.dto.request.UpdateMyWordStorageStatusDto;
import com.wolves.mainproject.dto.response.WordStorageWithNoWordDto;
import com.wolves.mainproject.exception.category.CategoryNotFoundException;
import com.wolves.mainproject.exception.wordStorage.WordStorageNotFoundException;
import com.wolves.mainproject.exception.wordStorage.WordStorageUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyWordStorageService {
    private final WordStorageRepository wordStorageRepository;
    private final WordStorageCategoryRepository wordStorageCategoryRepository;
    private final WordStorageLikeRepository wordStorageLikeRepository;

    @Transactional
    public void insertWordStorage(User user, RequestMyWordStorageDto dto){
        WordStorageCategory category = wordStorageCategoryRepository.findByName(dto.getCategory()).orElseThrow(CategoryNotFoundException::new);
        wordStorageRepository.save(dto.toWordStorage(category, user));
    }

    @Transactional
    public void updateWordStorage(User user, RequestMyWordStorageDto dto, long wordStorageId){
        WordStorage wordStorage = getWordStorageWithCredential(user, wordStorageId);
        WordStorageCategory category = wordStorageCategoryRepository.findByName(dto.getCategory()).orElseThrow(CategoryNotFoundException::new);
        wordStorage.update(dto, category);
    }

    @Transactional
    public void postBookmarkedWordStorage(User user, PostBookmarkedWordStorageDto dto, long wordStorageId){
        WordStorage wordStorage = getWordStorageWithCredential(user, wordStorageId);
        wordStorage.update(dto);
    }

    @Transactional
    public void updateWordStorageStatus(User user, UpdateMyWordStorageStatusDto dto, long wordStorageId){
        WordStorage wordStorage = getWordStorageWithCredential(user, wordStorageId);
        wordStorage.update(dto);
    }

    @Transactional(readOnly = true)
    public Page<WordStorageWithNoWordDto> findLikeWordStorageList(User user, Pageable pageable){
        Page<WordStorageLikeMapping> mappings = wordStorageLikeRepository.findAllByUser(user, pageable);
        return mappings.map(wordStorageLikeMapping -> new WordStorageWithNoWordDto(wordStorageLikeMapping.getWordStorage()));
    }

    private WordStorage getWordStorageWithCredential(User user, long wordStorageId){
        WordStorage wordStorage = wordStorageRepository.findById(wordStorageId).orElseThrow(WordStorageNotFoundException::new);
        if (!user.equals(wordStorage.getUser()))
            throw new WordStorageUnauthorizedException();
        return wordStorage;
    }

}
