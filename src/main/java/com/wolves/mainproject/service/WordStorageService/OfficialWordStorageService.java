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
public class OfficialWordStorageService {

    private final WordStorageRepository wordStorageRepository;
    private final WordStorageCategoryRepository wordStorageCategoryRepository;
    private final WordStorageLikeRepository wordStorageLikeRepository;
    private final WordRepository wordRepository;

    @Transactional
    public List<WordStorageMapping> getOfficialWordStorageOrderByLike(int page) {
        PageRequest pageRequest = PageRequest.of(page-1,10, Sort.by(Sort.Direction.DESC,"id"));

        return wordStorageRepository.findByTypeOrderByLikeCountDesc(WordStorageType.OFFICIAL.getType(), pageRequest);
    }

    @Transactional
    public List<WordStorageMapping> getOfficialWordStorageByCategory(String category, int page) {
        PageRequest pageRequest = PageRequest.of(page-1,10, Sort.by(Sort.Direction.DESC,"id"));

        WordStorageCategory searchCategory = wordStorageCategoryRepository.findByName(category)
                .orElseThrow(CategoryNotFoundException::new);

        return wordStorageRepository.findByTypeAndWordStorageCategory(WordStorageType.OFFICIAL.getType(), searchCategory, pageRequest);
    }

    @Transactional
    public List<WordStorageMapping> getOfficialWordStorageByTitle(String search, int page) {
        PageRequest pageRequest = PageRequest.of(page-1,10, Sort.by(Sort.Direction.DESC,"id"));

        return wordStorageRepository.findByTypeAndTitleContaining(WordStorageType.OFFICIAL.getType(), search, pageRequest);
    }

    @Transactional
    public WordStorageDetailResponseDto getOfficialWordStorageDetails(Long id, PrincipalDetails principalDetails) {
        WordStorage wordStorage = wordStorageRepository.findByTypeAndId(WordStorageType.OFFICIAL.getType(), id)
                .orElseThrow(WordStorageNotFoundException::new);

        Word wordList = wordRepository.findById(wordStorage.getId())
                .orElseThrow(WordNotFoundException::new);


        return new WordStorageDetailResponseDto(new WordInfoDto(wordList), wordStorage);
    }

    @Transactional
    // ERD에는 좋아요 생성일이 있는데 Entity에는 없다. 일단 DB 기준으로.
    public String likeOfficialWordStorage(Long id, PrincipalDetails principalDetails) {
        WordStorage likeWordStorage = wordStorageRepository.findByTypeAndId(WordStorageType.OFFICIAL.getType(),id)
                .orElseThrow(WordStorageNotFoundException::new);
        WordStorageLike wordStorageLike = wordStorageLikeRepository.findByUserAndWordStorage(principalDetails.getUser(), likeWordStorage);

        if(wordStorageLike == null) {
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
    public String putInMyWordStorage(Long id, PrincipalDetails principalDetails) {
        long newWordStorageId = saveWordStorage(id, principalDetails);

        saveWord(newWordStorageId);

        return null;
    }

    private void saveWord(long newWordStorageId) {
        Word word = wordRepository.findById(newWordStorageId)
                .orElseThrow(WordNotFoundException::new);

        word.setWordStorageId(newWordStorageId);

        wordRepository.save(word);
    }

    private long saveWordStorage(Long id, PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
        WordStorage wordStorage = wordStorageRepository.findById(id)
                .orElseThrow(WordStorageNotFoundException::new);
        WordStorageCategory category = wordStorageCategoryRepository.findByName(WordStorageType.PRIVATE.getType())
                .orElseThrow(CategoryNotFoundException::new);

        WordStorage newPrivateWordStorage = new WordStorage(user, wordStorage, category, WordStorageType.PRIVATE.getType());

        return wordStorageRepository.save(newPrivateWordStorage).getId();
    }


}
