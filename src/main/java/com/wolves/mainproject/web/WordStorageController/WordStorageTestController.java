package com.wolves.mainproject.web.WordStorageController;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.word.storage.answer.WrongAnswerMapping;
import com.wolves.mainproject.dto.request.exam.FinishWordExamRequestDto;
import com.wolves.mainproject.dto.request.exam.WordExamRequestDto;
import com.wolves.mainproject.dto.response.exam.FinishWordExamResponseDto;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.WordStorageService.WordStorageTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WordStorageTestController {

    private final WordStorageTestService wordStorageTestService;

    // 단어 시험 문제 생성
    @AuthValidation
    @PostMapping("/api/user/wordstorage/test")
    public ResponseEntity<Object> createWordProblems(@RequestBody WordExamRequestDto wordExamDto,
                                     @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        return ResponseEntity.ok(wordStorageTestService.createWordExam(wordExamDto, principalDetails));

    }

    // 단어 시험 종료
    @AuthValidation
    @PostMapping("/api/user/wordstorage/test/end")
    public ResponseEntity<FinishWordExamResponseDto> finishWordExam(@RequestBody FinishWordExamRequestDto finishWordExamDto,
                                                                    @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        return ResponseEntity.ok(wordStorageTestService.finishWordExam(finishWordExamDto, principalDetails));

    }

    // 단어 시험 History 조회
    @AuthValidation
    @GetMapping("/api/user/wordstorage/test/history")
    public ResponseEntity<List<WrongAnswerMapping>> getExamHistory(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return ResponseEntity.ok(wordStorageTestService.getExamHistory(principalDetails));

    }


}
