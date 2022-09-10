package com.wolves.mainproject.service;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.dto.UserResponseDto;
import com.wolves.mainproject.dto.request.PasswordDto;
import com.wolves.mainproject.dto.request.UserDto;
import com.wolves.mainproject.exception.Common.CommonInvalidInputValue;
import com.wolves.mainproject.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MyPageService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 암호 입력을 통한 회원정보 조회
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserToPassword(PasswordDto requestDto,
                                               PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        if (optionalUser == null) {
            throw new UserNotFoundException();
        }
        if (!checkPassword(requestDto, optionalUser)) {
            throw new CommonInvalidInputValue();
        }
        return new ResponseEntity<>(buildUserDto(optionalUser),HttpStatus.OK);
    }

    // 회원정보 수정
    @Transactional
    public ResponseEntity<?> updateUser(UserDto requestDto,
                                        PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        if (optionalUser == null) {
            throw new UserNotFoundException();
        }
        userRepository.save(buildUser(requestDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 비밀번호 변경
    @Transactional
    public ResponseEntity<?> updatePassword(PasswordDto requestDto,
                                            PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        if (optionalUser == null) {
            throw new UserNotFoundException();
        }
        userRepository.save(requestDto.toUser(passwordEncoder.encode(requestDto.getPassword()),optionalUser));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원탈퇴
    @Transactional(readOnly = true)
    public ResponseEntity<?> deleteUser(PasswordDto requestDto,
                                        PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        if (optionalUser == null) {
            throw new UserNotFoundException();
        }
        if (!checkPassword(requestDto, optionalUser)) {
            throw new CommonInvalidInputValue();
        }
        userRepository.delete(optionalUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // 유저 리스폰스 빌드
    @Transactional
    public UserResponseDto buildUserDto(User user) {
        return UserResponseDto.builder()
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .ageGroup(user.getAgeGroup())
                .gender(user.getGender())
                .build();
    }

    // 유저빌드 / 위 아래 메소드를 하나로 어찌 합쳐야할까...
    @Transactional
    public User buildUser(UserDto requestDto) {
        return User.builder()
                .nickname(requestDto.getNickname())
                .profileImage(requestDto.getProfileImage())
                .ageGroup(requestDto.getAgeGroup())
                .gender(requestDto.getGender())
                .build();
    }

    // 유저체크
    @Transactional(readOnly = true)
    public User checkUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    // 비밀번호 확인
    @Transactional(readOnly = true)
    public boolean checkPassword(PasswordDto requestDto,
                                 User user) {
        return passwordEncoder.matches(requestDto.getPassword(), user.getPassword());
    }

}
