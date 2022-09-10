package com.wolves.mainproject.web;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.request.FrequentlyQuestionDto;
import com.wolves.mainproject.service.FrequentlyQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FrequentlyQuestionController {

    private final FrequentlyQuestionService frequentlyQuestionService;


    @PostMapping("/api/support/frequently")
    public ResponseEntity<?> createFrequently(@RequestBody FrequentlyQuestionDto requestDto,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return frequentlyQuestionService.createFrequently(requestDto, principalDetails);
    }

    @GetMapping("/api/support/frequently")
    public ResponseEntity<?> getAllFrequently() {
        return frequentlyQuestionService.getAllFrequently();
    }

    @PutMapping("/api/support/frequently/id/{frequentlyId}")
    public ResponseEntity<?> updateFrequently(@PathVariable Long frequentlyId,
                                              @RequestBody FrequentlyQuestionDto requestDto,
                                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return frequentlyQuestionService.updateFrequently(frequentlyId, requestDto, principalDetails);
    }

    @DeleteMapping("/api/support/frequently/id/{frequentlyId}")
    public ResponseEntity<?> deleteFrequently(@PathVariable Long frequentlyId){
        return frequentlyQuestionService.deleteFrequently(frequentlyId);
    }

}
