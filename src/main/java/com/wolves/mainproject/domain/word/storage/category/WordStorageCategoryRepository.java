package com.wolves.mainproject.domain.word.storage.category;

import com.wolves.mainproject.domain.word.storage.WordStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordStorageCategoryRepository extends JpaRepository<WordStorageCategory, Long> {
//    List<WordStorageCategory> findByWordStorage(List<WordStorage> wordStorageList);

}
