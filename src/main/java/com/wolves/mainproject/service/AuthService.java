package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.game.history.GameHistoryRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.dto.request.my.word.storage.RequestSignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final GameHistoryRepository gameHistoryRepository;

    @Transactional
    public void userRegister(RequestSignupDto dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User userPS = userRepository.save(dto.toUser());
        gameHistoryRepository.save(dto.toGameHistory(userPS));
    }
}
