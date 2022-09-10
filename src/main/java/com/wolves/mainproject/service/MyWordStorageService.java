package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
import com.wolves.mainproject.dto.request.RequestMyWordStorageDto;
import com.wolves.mainproject.exception.category.CategoryNotFoundException;
import com.wolves.mainproject.exception.wordStorage.WordStorageNotFoundException;
import com.wolves.mainproject.exception.wordStorage.WordStorageUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyWordStorageService {
    private final WordStorageRepository wordStorageRepository;
    private final WordStorageCategoryRepository wordStorageCategoryRepository;

    @Transactional
    public void insertWordStorage(User user, RequestMyWordStorageDto dto){
        WordStorageCategory category = wordStorageCategoryRepository.findByName(dto.getCategory()).orElseThrow(CategoryNotFoundException::new);
        wordStorageRepository.save(dto.toWordStorage(category, user));
    }

    @Transactional
    public void updateWordStorage(User user, RequestMyWordStorageDto dto, long wordStorageId){
        WordStorage wordStorage = wordStorageRepository.findById(wordStorageId).orElseThrow(WordStorageNotFoundException::new);
        if (!user.equals(wordStorage.getUser()))
            throw new WordStorageUnauthorizedException();
        WordStorageCategory category = wordStorageCategoryRepository.findByName(dto.getCategory()).orElseThrow(CategoryNotFoundException::new);
        wordStorage.update(dto, category);
    }

}
