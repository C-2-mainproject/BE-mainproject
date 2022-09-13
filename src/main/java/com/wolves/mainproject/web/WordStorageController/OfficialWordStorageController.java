package com.wolves.mainproject.web.WordStorageController;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.dynamo.word.WordRepository;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.WordService;
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
    public ResponseEntity<Object> getOfficialWordStorageOrderByLike(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                    @RequestParam int page

    ){
        return ResponseEntity.ok(officialWordStorageService.getOfficialWordStorageOrderByLike(page));
    }

    // 공인 단어장 카테고리로 검색 (search)
    @GetMapping("/api/wordstorage/official/category")
    public ResponseEntity<Object> getOfficialWordStorageByCategory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                   @RequestParam String search,
                                                                   @RequestParam int page
    ){
        return ResponseEntity.ok(officialWordStorageService.getOfficialWordStorageByCategory(search, page));

    }

    // 공인 단어장 제목으로 검색 (search)
    @GetMapping("/api/wordstorage/official/title")
    public ResponseEntity<Object> getOfficialWOrdStorageByTitle(@RequestParam String search,
                                                                @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                @RequestParam int page
    ){
        return ResponseEntity.ok(officialWordStorageService.getOfficialWordStorageByTitle(search, page));

    }

    // 공인 단어장 상세 조회
    @AuthValidation
    @GetMapping("/api/wordstorage/official/{id}")
    public ResponseEntity<Object> getOfficialWordStorageDetails(@PathVariable Long id,
                                                                @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        return ResponseEntity.ok(officialWordStorageService.getOfficialWordStorageDetails(id, principalDetails));

    }

    // 공인 단어장 추천
    @AuthValidation
    @PostMapping("/api/wordstorage/official/like/{id}")
    public ResponseEntity<String> likeOfficialWordStorage(@PathVariable Long id,
                                                          @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        return ResponseEntity.ok(officialWordStorageService.likeOfficialWordStorage(id, principalDetails));

    }

    // 내 단어장에 넣기
    @AuthValidation
    @PostMapping("/api/wordstorage/save/id/{id}")
    public ResponseEntity<String> putInMyWordStorage(@PathVariable Long id,
                                                     @AuthenticationPrincipal PrincipalDetails principalDetails
    ){
        return ResponseEntity.ok(officialWordStorageService.putInMyWordStorage(id, principalDetails));

    }


// 내 단어장에서 단어나 단어장 뿌려줬을 떄 무한스크롤 진행.
// 마이페이지는 페이지네이션
// 메인 페이지는 페이지네이션

}
