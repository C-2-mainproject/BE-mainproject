package com.wolves.mainproject.domain.word.storage;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.category.CategoryStatisticMapping;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WordStorageRepository extends JpaRepository<WordStorage, Long> {
    Page<WordStorage> findAllByUser(User user, Pageable pageable);
    List<WordStorage> findAllByTitleContainingOrDescriptionContainingAndUser(String title, String description, User user);

    List<WordStorageMapping> findByTypeOrderByLikeCountDesc(String official, PageRequest pageRequest);

    List<WordStorageMapping> findByTypeAndWordStorageCategory(String official, WordStorageCategory searchCategory, PageRequest pageRequest);
    List<WordStorageMapping> findByTypeAndTitleContaining(String type, String title, PageRequest pageRequest);

    Optional<WordStorage> findByTypeAndId(String type, Long id);

    @Query(value = "SELECT word_storage_category.name as categoryName, COUNT(*) " +
            "AS count FROM word_storage " +
            "left join word_storage_category " +
            "on word_storage_category.id = word_storage.category_id " +
            "GROUP BY word_storage_category.name", nativeQuery = true)
    List<CategoryStatisticMapping> countByCategory();





}
