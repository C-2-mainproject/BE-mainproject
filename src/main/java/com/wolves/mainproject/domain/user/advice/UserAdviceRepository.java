package com.wolves.mainproject.domain.user.advice;

import com.wolves.mainproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAdviceRepository extends JpaRepository<UserAdvice, Long> {
    List<UserAdvice> findByUser(User user);

}
