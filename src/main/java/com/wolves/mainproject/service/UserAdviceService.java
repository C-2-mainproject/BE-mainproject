package com.wolves.mainproject.service;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.domain.user.advice.UserAdvice;
import com.wolves.mainproject.domain.user.advice.UserAdviceRepository;
import com.wolves.mainproject.dto.request.ReplyAdviceDto;
import com.wolves.mainproject.dto.request.UserAdviceDto;
import com.wolves.mainproject.exception.user.UserNotFoundException;
import com.wolves.mainproject.exception.user.advice.UserAdviceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserAdviceService {

    private final UserRepository userRepository;
    private final UserAdviceRepository adviceRepository;


    // 1대1 문의 생성
    @Transactional
    public ResponseEntity<?> createAdvice(UserAdviceDto requestDto,
                                          PrincipalDetails principalDetails) {
        checkUser(principalDetails.getUser().getId());
        adviceRepository.save(buildAdvice(requestDto, principalDetails));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 1대1 문의 답변여부 수정
    @Transactional
    public ResponseEntity<?> updateAdvice(Long adviceId, ReplyAdviceDto requestDto,
                                          PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        if (optionalUser == null) {
            throw new UserNotFoundException();
        }
        UserAdvice optionalAdvice = checkAdvice(adviceId);
        if (optionalAdvice == null) {
            throw new UserAdviceNotFoundException();
        }
        adviceRepository.save(buildReplyAdvice(requestDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // 유저 찾기
    @Transactional(readOnly = true)
    public User checkUser(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    // 1대1 문의 찾기
    @Transactional(readOnly = true)
    public UserAdvice checkAdvice(Long adviceId) {
        Optional<UserAdvice> optionalAdvice = adviceRepository.findById(adviceId);
        return optionalAdvice.orElse(null);
    }

    // 1대1 문의 빌드하기
    @Transactional
    public UserAdvice buildAdvice(UserAdviceDto requestDto, PrincipalDetails principalDetails) {
        return UserAdvice.builder()
                .title(requestDto.getTitle())
                .category(requestDto.getCategory())
                .content(requestDto.getContent())
                .evidenceImage(requestDto.getEvidenceImage())
                .personalConsent(requestDto.getPersonalConsent())
                .user(principalDetails.getUser())
                .build();
    }

    // 1대1 문의 답변여부 빌드
    @Transactional
    public UserAdvice buildReplyAdvice(ReplyAdviceDto requestDto) {
        return UserAdvice.builder()
                .isClear(requestDto.getIsClear())
                .build();
    }
}
