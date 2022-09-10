package com.wolves.mainproject.domain.word;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {
    Optional<List<WordMapping>> findAllByWord(String word);
}
