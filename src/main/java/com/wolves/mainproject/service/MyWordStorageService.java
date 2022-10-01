package com.wolves.mainproject.service;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.dynamo.word.Word;
import com.wolves.mainproject.domain.dynamo.word.WordRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.WordStorageMapping;
import com.wolves.mainproject.domain.word.storage.WordStorageRepository;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategoryRepository;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeMapping;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLikeRepository;
import com.wolves.mainproject.dto.request.my.word.storage.PostBookmarkedWordStorageDto;
import com.wolves.mainproject.dto.request.my.word.storage.RequestMyWordStorageDto;
import com.wolves.mainproject.dto.request.my.word.storage.UpdateMyWordStorageStatusDto;
import com.wolves.mainproject.dto.request.my.word.storage.UpdateWordDto;
import com.wolves.mainproject.dto.response.WordDto;
import com.wolves.mainproject.dto.response.WordStorageResponseDto;
import com.wolves.mainproject.dto.response.WordStorageWithNoWordDto;
import com.wolves.mainproject.exception.category.CategoryNotFoundException;
import com.wolves.mainproject.exception.word.WordNotAcceptableException;
import com.wolves.mainproject.exception.word.WordNotFoundException;
import com.wolves.mainproject.exception.wordStorage.WordStorageNotAcceptableException;
import com.wolves.mainproject.exception.wordStorage.WordStorageNotFoundException;
import com.wolves.mainproject.exception.wordStorage.WordStorageUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyWordStorageService {
    private final WordStorageRepository wordStorageRepository;
    private final WordStorageCategoryRepository wordStorageCategoryRepository;
    private final WordStorageLikeRepository wordStorageLikeRepository;
    private final WordRepository wordRepository;

    @Transactional
    public void insertWordStorage(User user, RequestMyWordStorageDto dto){
        WordStorageCategory category = wordStorageCategoryRepository.findByName(dto.getCategory()).orElseThrow(CategoryNotFoundException::new);
        WordStorage wordStorage = wordStorageRepository.save(dto.toWordStorage(category, user));
        wordStorageCountValidate(wordStorageRepository.countByUser(user));
        wordRepository.save(dto.toWord(wordStorage.getId()));
    }

    @Transactional
    public void updateWordStorage(User user, RequestMyWordStorageDto dto, long wordStorageId){
        WordStorage wordStorage = getWordStorageWithCredential(user, wordStorageId);
        WordStorageCategory category = wordStorageCategoryRepository.findByName(dto.getCategory()).orElseThrow(CategoryNotFoundException::new);
        wordStorage.update(dto, category, user);
    }

    @Transactional
    public void deleteWordStorage(User user, long wordStorageId){
        WordStorage wordStorage = getWordStorageWithCredential(user, wordStorageId);
        wordStorageRepository.delete(wordStorage);
        wordRepository.delete(Word.builder().wordStorageId(wordStorageId).build());
    }

    @Transactional
    public void postBookmarkedWordStorage(User user, PostBookmarkedWordStorageDto dto, long wordStorageId){
        WordStorage wordStorage = getWordStorageWithCredential(user, wordStorageId);
        wordStorage.update(dto);
    }

    @Transactional
    public void updateWordStorageStatus(User user, UpdateMyWordStorageStatusDto dto, long wordStorageId){
        WordStorage wordStorage = getWordStorageWithCredential(user, wordStorageId);
        wordStorage.update(dto, wordStorage);
    }

    @Transactional(readOnly = true)
    public List<WordStorageWithNoWordDto> findLikeWordStorageList(User user){
        List<WordStorageLikeMapping> mappings = wordStorageLikeRepository.findAllByUser(user);
        return mappings.stream().map(wordStorageLikeMapping -> new WordStorageWithNoWordDto(wordStorageLikeMapping.getWordStorage())).toList();
    }

    @Transactional(readOnly = true)
    public List<WordStorageWithNoWordDto> findMyWordStorages(User user){
        List<WordStorage> wordStorages = wordStorageRepository.findAllByUser(user);
        return wordStorages.stream().map(WordStorageWithNoWordDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<WordStorageWithNoWordDto> findSearchInMyWordStorage(User user, String searchData){
        List<WordStorage> wordStorages = wordStorageRepository.findAllByTitleContainingOrDescriptionContainingAndUser(searchData, searchData, user);
        return wordStorages.stream().map(WordStorageWithNoWordDto::new).toList();
    }

    @Transactional(readOnly = true)
    public WordDto findWordInMyWordStorage(User user, long wordStorageId){
        WordStorage wordStorage = getWordStorageWithCredential(user, wordStorageId);
        Word word = wordRepository.findById(wordStorage.getId()).orElseThrow(WordNotFoundException::new);
        return new WordDto(word.getWords(), word.getMeanings());
    }

    public void updateWordInMyWordStorage(User user, UpdateWordDto dto, long wordStorageId){
        WordStorage wordStorage = getWordStorageWithCredential(user, wordStorageId);
        wordRepository.save(dto.toWord(wordStorage.getId()));
    }

    // @TODO : Need change to use jpa only
    private WordStorage getWordStorageWithCredential(User user, long wordStorageId){
        WordStorage wordStorage = wordStorageRepository.findById(wordStorageId).orElseThrow(WordStorageNotFoundException::new);
        if (user.getId() != wordStorage.getUser().getId())
            throw new WordStorageUnauthorizedException();
        return wordStorage;
    }


    private void wordStorageCountValidate(long count){
        // @TODO : Need merge final value to one site
        long maxWordStorage = 40;
        if (count > maxWordStorage)
            throw new WordStorageNotAcceptableException();
    }

    
    public List<WordStorageResponseDto> setHaveStorageFlags(PrincipalDetails principalDetails, List<WordStorage> wordStorages) {
        List<WordStorageResponseDto> result = new ArrayList<>();

        for(WordStorage wordStorage : wordStorages){
            result.add(setHaveStorageFlag(principalDetails, wordStorage));
        }
        return result;
    }

    private WordStorageResponseDto setHaveStorageFlag(PrincipalDetails principalDetails, WordStorage originalWordStorage) {
        WordStorageResponseDto wordStorageResponseDto = new WordStorageResponseDto(originalWordStorage);

        if(principalDetails != null){
            wordStorageResponseDto.setHaveStorage(wordStorageRepository.existsByUserAndOriginalWordStorage(principalDetails.getUser(), originalWordStorage));
        } else {
            wordStorageResponseDto.setHaveStorage(false);
        }

        return wordStorageResponseDto;
    }


}
