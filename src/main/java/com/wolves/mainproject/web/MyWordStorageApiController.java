package com.wolves.mainproject.web;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.request.PostBookmarkedWordStorageDto;
import com.wolves.mainproject.dto.request.RequestMyWordStorageDto;
import com.wolves.mainproject.dto.request.UpdateMyWordStorageStatusDto;
import com.wolves.mainproject.dto.request.UpdateWordDto;
import com.wolves.mainproject.dto.response.WordDto;
import com.wolves.mainproject.dto.response.WordStorageWithNoWordDto;
import com.wolves.mainproject.handler.aop.annotation.AuthValidation;
import com.wolves.mainproject.service.MyWordStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyWordStorageApiController {
    private final MyWordStorageService myWordStorageService;

    @AuthValidation
    @PostMapping("/api/user/wordstorage")
    public ResponseEntity<Void> createWordStorage(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody RequestMyWordStorageDto dto){
        myWordStorageService.insertWordStorage(principalDetails.getUser(), dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AuthValidation
    @PutMapping("/api/user/wordstorage/id/{wordStorageId}")
    public ResponseEntity<Void> updateWordStorage(@PathVariable long wordStorageId, @AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody RequestMyWordStorageDto dto){
        myWordStorageService.updateWordStorage(principalDetails.getUser(), dto, wordStorageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AuthValidation
    @PutMapping("/api/user/wordstorage/id/{wordStorageId}/bookmark")
    public ResponseEntity<Void> postBookmarkedWordStorage(@PathVariable long wordStorageId, @AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody PostBookmarkedWordStorageDto dto){
        myWordStorageService.postBookmarkedWordStorage(principalDetails.getUser(), dto, wordStorageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AuthValidation
    @PutMapping("/api/user/wordstorage/id/{wordStorageId}/status")
    public ResponseEntity<Void> updateWordStorageStatus(@PathVariable long wordStorageId, @AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody UpdateMyWordStorageStatusDto dto){
        myWordStorageService.updateWordStorageStatus(principalDetails.getUser(), dto, wordStorageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AuthValidation
    @GetMapping("/api/user/wordstorage/like")
    public ResponseEntity<Page<WordStorageWithNoWordDto>> findLikeWordStorageList(@AuthenticationPrincipal PrincipalDetails principalDetails, @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(myWordStorageService.findLikeWordStorageList(principalDetails.getUser(), pageable), HttpStatus.OK);
    }

    @AuthValidation
    @GetMapping("/api/user/wordstorage/my")
    public ResponseEntity<Page<WordStorageWithNoWordDto>> findMyWordStorages(@AuthenticationPrincipal PrincipalDetails principalDetails, @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(myWordStorageService.findMyWordStorages(principalDetails.getUser(), pageable), HttpStatus.OK);
    }

    @AuthValidation
    @GetMapping("/api/user/wordstorage/my/search")
    public ResponseEntity<List<WordStorageWithNoWordDto>> findSearchInMyWordStorage(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam String search){
        return new ResponseEntity<>(myWordStorageService.findSearchInMyWordStorage(principalDetails.getUser(), search), HttpStatus.OK);
    }

    @AuthValidation
    @GetMapping("/api/user/wordstorage/id/{wordStorageId}")
    public ResponseEntity<WordDto> findWordInMyWordStorage(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long wordStorageId){
        return new ResponseEntity<>(myWordStorageService.findWordInMyWordStorage(principalDetails.getUser(), wordStorageId), HttpStatus.OK);
    }

    @AuthValidation
    @PutMapping("/api/user/wordstorage/id/{wordStorageId}/word")
    public ResponseEntity<Void> updateWordInMyWordStorage(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long wordStorageId, @RequestBody UpdateWordDto dto){
        myWordStorageService.updateWordInMyWordStorage(principalDetails.getUser(), dto, wordStorageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
