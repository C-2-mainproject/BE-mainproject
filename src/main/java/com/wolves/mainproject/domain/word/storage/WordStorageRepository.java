package com.wolves.mainproject.domain.word.storage;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.category.CategoryStatisticMapping;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.type.StatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface WordStorageRepository extends JpaRepository<WordStorage, Long> {

    boolean existsByUserAndOriginalWordStorage(User user, WordStorage originalWordStorage);

    Page<WordStorage> findAllByUser(User user, Pageable pageable);
    List<WordStorage> findAllByTitleContainingOrDescriptionContainingAndUser(String title, String description, User user);

    List<WordStorageMapping> findByStatusOrderByLikeCountDesc(StatusType status, PageRequest pageRequest);

    List<WordStorageMapping> findByStatusAndWordStorageCategory(StatusType status, WordStorageCategory searchCategory, PageRequest pageRequest);
    List<WordStorageMapping> findByStatusAndTitleContaining(StatusType status, String title, PageRequest pageRequest);

    Optional<WordStorage> findByStatusAndId(StatusType status, Long id);

    @Query(value = "SELECT word_storage_category.name as categoryName, COUNT(*) " +
            "AS count FROM word_storage " +
            "left join word_storage_category " +
            "on word_storage_category.id = word_storage.category_id " +
            "GROUP BY word_storage_category.name", nativeQuery = true)
    List<CategoryStatisticMapping> countByCategory();
}
