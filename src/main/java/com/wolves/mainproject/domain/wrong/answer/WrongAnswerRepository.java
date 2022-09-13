package com.wolves.mainproject.domain.wrong.answer;

import com.wolves.mainproject.domain.word.storage.answer.WrongAnswerMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WrongAnswerRepository extends JpaRepository<WrongAnswer, Long> {

    @Query(value = "SELECT m FROM WrongAnswer AS m")
    List<WrongAnswerMapping> customFindALl();

}
