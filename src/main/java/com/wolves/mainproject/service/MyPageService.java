package com.wolves.mainproject.service;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.domain.user.advice.UserAdvice;
import com.wolves.mainproject.domain.user.advice.UserAdviceRepository;
import com.wolves.mainproject.dto.UserResponseDto;
import com.wolves.mainproject.dto.request.UserDto;
import com.wolves.mainproject.dto.response.InquiryDto;
import com.wolves.mainproject.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MyPageService {

    private final UserRepository userRepository;
    private final UserAdviceRepository userAdviceRepository;

    // 암호 입력을 통한 회원정보 조회
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserInfo(PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        return new ResponseEntity<>(buildUserDto(optionalUser), HttpStatus.OK);
    }

    // 회원정보 수정
    @Transactional
    public ResponseEntity<?> updateUser(UserDto requestDto,
                                        PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        userRepository.save(requestDto.toUser(optionalUser));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원탈퇴
    @Transactional
    public ResponseEntity<?> deleteUser(PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        userRepository.deleteById(optionalUser.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // 유저 리스폰스 빌드
    @Transactional
    public UserResponseDto buildUserDto(User user) {
        return UserResponseDto.builder()
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .build();
    }

    // 유저체크
    @Transactional(readOnly = true)
    public User checkUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        return optionalUser.orElse(null);
    }


    @Transactional(readOnly = true)
    public List<InquiryDto> getUserQuestion(PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
        List<UserAdvice> userAdviceList = userAdviceRepository.findByUser(user);
        return userAdviceList.stream().map(userAdvice -> new InquiryDto(userAdvice)).toList();
    }

    @Transactional(readOnly = true)
    public List<InquiryDto> getAdminQuestion(PrincipalDetails principalDetails) {
        checkUser(principalDetails.getUser().getId());
        List<UserAdvice> userAdviceList = userAdviceRepository.findAll();
        return userAdviceList.stream().map(userAdvice -> new InquiryDto(userAdvice)).toList();
    }


}
