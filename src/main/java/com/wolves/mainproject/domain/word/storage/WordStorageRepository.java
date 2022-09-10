package com.wolves.mainproject.domain.word.storage;

import com.wolves.mainproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordStorageRepository extends JpaRepository<WordStorage, Long> {
    List<WordStorage> findAllByUser(User user);
}
