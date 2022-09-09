package com.wolves.mainproject.service;

import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.dto.request.RequestSignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void userRegister(RequestSignupDto dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(dto.toUser());
    }
}
