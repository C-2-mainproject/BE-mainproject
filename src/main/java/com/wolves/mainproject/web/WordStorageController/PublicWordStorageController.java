package com.wolves.mainproject.web.WordStorageController;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.service.WordStorageService.PublicWordStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PublicWordStorageController {

    private final PublicWordStorageService publicWordStorageService;

    // 회원 공유 단어장 인기 순 조회 (filter)
    @GetMapping("/api/wordstorage/public/filter/like")
    public ResponseEntity<Object> getPublicWordStorageOrderByLikes(@RequestParam int page) {
        return ResponseEntity.ok(publicWordStorageService.getPublicWordStorageOrderByLikes(page));
    }

    // 회원 공유 단어장 카테고리로 검색 (search)
    @GetMapping("/api/wordstorage/public/filter")
    public ResponseEntity<Object> getPublicWordStorageOrderByCategory(@RequestParam String search,
                                                                      @RequestParam int page
    ){
        return ResponseEntity.ok(publicWordStorageService.getPublicWordStorageByCategory(search, page));

    }

    // 회원 공유 단어장 제목으로 검색 (search)
    @GetMapping("/api/wordstorage/public/title")
    public ResponseEntity<Object> getPublicWordStorageByTitle(@RequestParam String search,
                                                              @RequestParam int page
    ){
        return ResponseEntity.ok(publicWordStorageService.getPublicWordStorageByTitle(search, page));

    }

    // 회원 공유 단어장 상세 조회
    @GetMapping("/api/wordstorage/public/{id}")
    public ResponseEntity<Object> getPublicWordStorageDetails(@PathVariable Long id,
                                                              @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        return ResponseEntity.ok(publicWordStorageService.getPublicWordStorageDetails(id, principalDetails));

    }

    // 회원 공유 단어장 추천
    @PostMapping("/api/wordstorage/public/like/{id}")
    public ResponseEntity<String> likePublicWordStorage(@PathVariable Long id,
                                                        @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        return ResponseEntity.ok(publicWordStorageService.likePublicWordStorage(id, principalDetails));

    }

    // 단어장 통계 조회
    @GetMapping("/api/wordstorage/statistic")
    public ResponseEntity<Object> getWordStorageStatistics(){
        return ResponseEntity.ok(publicWordStorageService.getWordStorageStatistics());

    }


}
