package com.wolves.mainproject.domain.word.storage.like;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordStorageLikeRepository extends JpaRepository<WordStorageLike, Long> {
    List<WordStorage> findByUser(User user);

}
