package com.wolves.mainproject.web.WordStorageController;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.WordStorageService.OfficialWordStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OfficialWordStorageController {

    private final OfficialWordStorageService officialWordStorageService;


    // 공인 단어장 인기 순 조회 (filter)
    @GetMapping("/api/wordstorage/official/filter/like")
    public ResponseEntity<Object> getOfficialWordStoragesOrderByLike(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                     @RequestParam int page

    ){
        return ResponseEntity.ok(officialWordStorageService.getOfficialWordStoragesOrderByLike(page, principalDetails));
    }

    // 공인 단어장 카테고리로 검색 (search)
    @GetMapping("/api/wordstorage/official/category")
    public ResponseEntity<Object> getOfficialWordStoragesByCategory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                    @RequestParam String search,
                                                                    @RequestParam Long lastArticleId
    ){
        return ResponseEntity.ok(officialWordStorageService.getOfficialWordStoragesByCategory(search, lastArticleId, principalDetails));

    }

    // 공인 단어장 제목으로 검색 (search)
    @GetMapping("/api/wordstorage/official/title")
    public ResponseEntity<Object> getOfficialWOrdStoragesByTitle(@RequestParam String search,
                                                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                 @RequestParam Long lastArticleId
    ){
        return ResponseEntity.ok(officialWordStorageService.getOfficialWordStoragesByTitle(search, lastArticleId, principalDetails));

    }

    // 공인 단어장 상세 조회
    @AuthValidation
    @GetMapping("/api/wordstorage/official/{id}")
    public ResponseEntity<Object> getOfficialWordStorageDetails(@PathVariable Long id,
                                                                @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        return ResponseEntity.ok(officialWordStorageService.getOfficialWordStorageDetails(id));

    }

    // 단어장 추천
    @AuthValidation
    @PostMapping("/api/wordstorage/like/{id}")
    public ResponseEntity<String> likeOfficialWordStorage(@PathVariable Long id,
                                                          @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        return ResponseEntity.ok(officialWordStorageService.likeWordStorage(id, principalDetails));

    }

    // 내 단어장에 넣기
    @AuthValidation
    @PostMapping("/api/wordstorage/save/id/{id}")
    public ResponseEntity<String> copyToMyWordStorage(@PathVariable Long id,
                                                     @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        return ResponseEntity.ok(officialWordStorageService.copyToMyWordStorage(id, principalDetails));

    }

}
