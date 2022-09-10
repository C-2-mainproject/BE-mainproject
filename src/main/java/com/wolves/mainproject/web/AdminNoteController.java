package com.wolves.mainproject.web;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.dto.request.AdminNoteDto;
import com.wolves.mainproject.handler.aop.annotation.AdminValidation;
import com.wolves.mainproject.service.AdminNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AdminNoteController {

    private final AdminNoteService adminNoteService;

    @AdminValidation
    @PostMapping("/api/support/admin/note")
    public ResponseEntity<?> createNote(@RequestBody AdminNoteDto requestDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return adminNoteService.createNote(requestDto, principalDetails);
    }

    @GetMapping("/api/support/admin/note")
    public ResponseEntity<?> getAllNote() {
        return adminNoteService.getAllNote();
    }

    @AdminValidation
    @PutMapping("/api/support/admin/note/id/{noteId}")
    public ResponseEntity<?> updateNote(@PathVariable Long noteId,
                                        @RequestBody AdminNoteDto requestDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return adminNoteService.updateNote(noteId, requestDto, principalDetails);
    }

    @AdminValidation
    @DeleteMapping("/api/support/admin/note/id/{noteId}")
    public ResponseEntity<?> deleteNote(@PathVariable Long noteId){
        return adminNoteService.deleteNote(noteId);
    }
}