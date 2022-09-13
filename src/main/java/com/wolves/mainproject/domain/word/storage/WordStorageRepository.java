package com.wolves.mainproject.domain.word.storage;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wolves.mainproject.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface WordStorageRepository extends JpaRepository<WordStorage, Long> {

    Page<WordStorage> findAllByUser(User user, Pageable pageable);
    List<WordStorage> findAllByTitleContainingOrDescriptionContainingAndUser(String title, String description, User user);

}
