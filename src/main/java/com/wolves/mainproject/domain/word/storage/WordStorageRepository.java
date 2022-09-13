package com.wolves.mainproject.domain.word.storage;

import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.domain.word.storage.like.WordStorageLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordStorageRepository extends JpaRepository<WordStorage, Long> {
//    List<WordStorage> findByWordStorage(List<WordStorageLike> wordStorages);
//
//    List<WordStorage> findByWordStorageCategories(List<WordStorageCategory> wordStorageCategories);

}
