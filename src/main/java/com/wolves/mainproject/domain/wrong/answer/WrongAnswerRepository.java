package com.wolves.mainproject.domain.wrong.answer;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.answer.WrongAnswerMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WrongAnswerRepository extends JpaRepository<WrongAnswer, Long> {

    @Query(value = "SELECT m FROM WrongAnswer AS m WHERE m.user = :user")
    List<WrongAnswerMapping> customFindALlByUser(@Param("user") User user);

}
