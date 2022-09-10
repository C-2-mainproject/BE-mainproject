package com.wolves.mainproject.domain.word.storage.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordStorageCategoryRepository extends JpaRepository<WordStorageCategory, Long> {
    Optional<WordStorageCategory> findByName(String name);
}
