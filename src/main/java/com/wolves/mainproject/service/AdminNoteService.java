package com.wolves.mainproject.service;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.admin.note.AdminNote;
import com.wolves.mainproject.domain.admin.note.AdminNoteRepository;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.dto.AllAdminNoteResponseDto;
import com.wolves.mainproject.dto.request.AdminNoteDto;
import com.wolves.mainproject.exception.admin.note.AdminNoteNotFoundException;
import com.wolves.mainproject.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminNoteService {

    private final UserRepository userRepository;
    private final AdminNoteRepository noteRepository;

    // 관리자노트 생성
    @Transactional
    public ResponseEntity<?> createNote(AdminNoteDto requestDto,
                                        PrincipalDetails principalDetails) {
        checkUser(principalDetails.getUser().getId());
        noteRepository.save(buildNote(requestDto, principalDetails));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 관리자노트 전체조회
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllNote() {
        List<AllAdminNoteResponseDto> allNoteResponseDtoList = getAllNotes();
        return new ResponseEntity<>(allNoteResponseDtoList, HttpStatus.OK);
    }

    // 관리자노트 수정
    @Transactional
    public ResponseEntity<?> updateNote(Long noteId, AdminNoteDto requestDto,
                                        PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        AdminNote adminNote = checkNote(noteId);
        adminNote.update(requestDto);
        noteRepository.save(adminNote);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 관리자노트 삭제
    @Transactional
    public ResponseEntity<?> deleteNote(Long frequentlyId,
                                        PrincipalDetails principalDetails) {
        User optionalUser = checkUser(principalDetails.getUser().getId());
        AdminNote adminNote = checkNote(frequentlyId);
        noteRepository.delete(adminNote);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // 관리자노트 빌드하기
    @Transactional
    public AdminNote buildNote(AdminNoteDto requestDto,
                               PrincipalDetails principalDetails) {
        return AdminNote.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .user(principalDetails.getUser())
                .build();
    }

    // 관리자노트 전체 리스트화
    @Transactional
    public List<AllAdminNoteResponseDto> getAllNotes() {
        List<AdminNote> noteList = noteRepository.findAll();
        List<AllAdminNoteResponseDto> allNoteResponseDtoList = new ArrayList<>();
        for (AdminNote note: noteList) {
            AllAdminNoteResponseDto allNoteResponseDto = AllAdminNoteResponseDto.builder()
                    .id(note.getId())
                    .title(note.getTitle())
                    .content(note.getContent())
                    .build();
            allNoteResponseDtoList.add(allNoteResponseDto);
        }
        return allNoteResponseDtoList;
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

    // 관리자노트 찾기
    @Transactional(readOnly = true)
    public AdminNote checkNote(Long noteId) {
        Optional<AdminNote> optionalNote = noteRepository.findById(noteId);
        if (optionalNote.isEmpty()) {
            throw new AdminNoteNotFoundException();
        }
        return optionalNote.orElse(null);
    }
}