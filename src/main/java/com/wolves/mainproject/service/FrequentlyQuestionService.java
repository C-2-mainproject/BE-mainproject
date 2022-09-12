package com.wolves.mainproject.service;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.frequently.question.FrequentlyQuestion;
import com.wolves.mainproject.domain.frequently.question.FrequentlyQuestionRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.dto.AllFrequentlyQuestionResponseDto;
import com.wolves.mainproject.dto.request.FrequentlyQuestionDto;
import com.wolves.mainproject.exception.frequently.question.FrequentlyQuestionNotFoundException;
import com.wolves.mainproject.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FrequentlyQuestionService {

    private final UserRepository userRepository;
    private final FrequentlyQuestionRepository frequentlyQuestionRepository;

    // 자묻질 생성
    @Transactional
    public ResponseEntity<?> createFrequently(FrequentlyQuestionDto requestDto,
                                              PrincipalDetails principalDetails) {
        checkUser(principalDetails.getUser().getId());
        frequentlyQuestionRepository.save(buildFrequently(requestDto, principalDetails));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 자묻질 전체조회
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllFrequently() {
        List<AllFrequentlyQuestionResponseDto> allFrequentlyResponseDtoList = getAllFrequents();
        return new ResponseEntity<>(allFrequentlyResponseDtoList, HttpStatus.OK);
    }

    // 자묻질 수정
    @Transactional
    public ResponseEntity<?> updateFrequently(Long frequentlyId, FrequentlyQuestionDto requestDto,
                                              PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        FrequentlyQuestion frequently = checkFrequency(frequentlyId);
        frequently.update(requestDto);
        frequentlyQuestionRepository.save(frequently);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 자묻질 삭제
    @Transactional
    public ResponseEntity<?> deleteFrequently(Long frequentlyId, PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        FrequentlyQuestion frequently = checkFrequency(frequentlyId);
        frequentlyQuestionRepository.delete(frequently);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // 자묻질 빌드하기
    @Transactional
    public FrequentlyQuestion buildFrequently(FrequentlyQuestionDto requestDto,
                                              PrincipalDetails principalDetails) {
        return FrequentlyQuestion.builder()
                .title(requestDto.getTitle())
                .category(requestDto.getCategory())
                .reply(requestDto.getReply())
                .user(principalDetails.getUser())
                .build();
    }

    // 자묻질 전체 리스트화
    @Transactional
    public List<AllFrequentlyQuestionResponseDto> getAllFrequents() {
        List<FrequentlyQuestion> frequentlyList = frequentlyQuestionRepository.findAll();
        List<AllFrequentlyQuestionResponseDto> allFrequentlyResponseDtoList = new ArrayList<>();
        for (FrequentlyQuestion frequently: frequentlyList) {
            AllFrequentlyQuestionResponseDto allFrequentlyResponseDto = AllFrequentlyQuestionResponseDto.builder()
                    .title(frequently.getTitle())
                    .category(frequently.getCategory())
                    .reply(frequently.getReply())
                    .build();
            allFrequentlyResponseDtoList.add(allFrequentlyResponseDto);
        }
        return allFrequentlyResponseDtoList;
    }


    // 유저 찾기
    @Transactional(readOnly = true)
    public User checkUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        return optionalUser.orElse(null);
    }

    // 자묻질 찾기
    @Transactional(readOnly = true)
    public FrequentlyQuestion checkFrequency(Long frequentlyId) {
        Optional<FrequentlyQuestion> optionalFrequently = frequentlyQuestionRepository.findById(frequentlyId);
        if (optionalFrequently.isEmpty()) {
            throw new FrequentlyQuestionNotFoundException();
        }
        return optionalFrequently.orElse(null);
    }

}