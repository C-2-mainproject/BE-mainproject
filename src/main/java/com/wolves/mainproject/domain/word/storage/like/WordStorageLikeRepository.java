package com.wolves.mainproject.domain.word.storage.like;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordStorageLikeRepository extends JpaRepository<WordStorageLike, Long> {
    boolean existsByUserAndWordStorage(User user, WordStorage wordStorage);
    void deleteByUserAndWordStorage(User user, WordStorage wordStorage);
    List<WordStorageLikeMapping> findAllByUser(User user);

    WordStorageLike findByUserAndWordStorage(User user, WordStorage likeWordStorage);
}
