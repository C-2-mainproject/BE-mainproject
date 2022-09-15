package com.wolves.mainproject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {


    // hashmap 사용해서 바꿔보기

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "올바르지 않은 값입니다."),
    UNFILLED_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "필요한 값을 전부 입력하지 않았습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "C003", "페이지를 찾을 수 없습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C004", "접속이 거부되었습니다."),
    REQUEST_DENIED(HttpStatus.NOT_ACCEPTABLE, "C005", "허용되지 않은 IP입니다."),
    SQL_CONFLICT(HttpStatus.CONFLICT, "C006", "중복된 값이 존재합니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "해당 유저가 존재하지 않습니다."),
    USER_ALREADY_FOUND(HttpStatus.BAD_REQUEST, "U002","이미 로그인 된 사용자입니다." ),
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "U003","로그인이 필요한 서비스입니다." ),
    USER_ALREADY_VALID(HttpStatus.BAD_REQUEST, "U004", "이미 인증이 완료된 사용자입니다."),

    // Login
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "L001", "아이디 혹은 비밀번호가 일치하지 않습니다."),

    // Register
    EMAIL_CONFLICT(HttpStatus.CONFLICT, "R001", "이미 존재하는 이메일입니다."),
    NICKNAME_CONFLICT(HttpStatus.CONFLICT, "R002", "이미 존재하는 닉네임입니다."),
    FORM_NOT_VALID(HttpStatus.BAD_REQUEST, "R003", "형식이 올바르지 않습니다."),

    NICKNAME_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "R004", "닉네임 길이가 너무 깁니다."),
    EMAIL_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "R005", "이메일 길이가 너무 깁니다."),
    GENDER_CONFLICT(HttpStatus.CONFLICT, "R006", "성별 형식이 올바르지 않습니다."),

    // Board
    BOARD_PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "B001", "해당 게시글을 찾을 수 없습니다."),
    BOARD_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "B002", "해당 게시글에 대한 권한이 없습니다."),
    BOARD_COMMENT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "B003", "해당 댓글에 대한 권한이 없습니다."),
    BOARD_TITLE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "B004", "게시글 제목의 길이가 너무 깁니다."),
    BOARD_COMMENT_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "B005", "댓글 내용의 길이가 너무 깁니다."),
    BOARD_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "B006", "해당 댓글을 찾을 수 없습니다."),


    // Question
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Q001", "해당 질문을 찾을 수 없습니다."),
    QUESTION_TITLE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "Q002","질문의 제목이 너무 깁니다."),
    QUESTION_CATEGORY_NOT_VALID(HttpStatus.BAD_REQUEST, "Q003", "존재하지 않는 카테고리명입니다."),


    // Advice
    ADVICE_NOT_FOUND(HttpStatus.NOT_FOUND, "A001", "해당 문의 글을 찾을 수 없습니다."),
    ADVICE_CATEGORY_NOT_VALID(HttpStatus.BAD_REQUEST, "A002", "존재하지 않는 카테고리명입니다."),
    ADVICE_TITLE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "A003", "질문 제목이 너무 깁니다."),
    ADVICE_CONTENT_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "A004", "질문 내용이 너무 깁니다."),
    ADVICE_IMAGE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "A005", "질문 이미지 URL의 길이가 너무 깁니다."),

    // Admin note
    ADMINNOTE_NOT_FOUND(HttpStatus.BAD_REQUEST, "A101", "해당 게시글을 찾을 수 없습니다."),
    ADMINNOTE_TITLE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "A102", "게시글 제목이 너무 깁니다."),
    ADMINNOTE_CONTENT_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "A103", "게시글 내용이 너무 깁니다."),

    // Wrong Answer
    TEST_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "W001", "존재하지 않는 시험 타입입니다."),

    // Word Storage
    WORD_STORAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "W101", "존재하지 않는 단어장입니다."),
    WORD_STORAGE_TITLE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "W102", "단어장 제목이 너무 깁니다."),
    WORD_STORAGE_DESCRIPTION_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "W103", "단어장 설명이 너무 깁니다."),
    WORD_STORAGE_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "W104", "존재하지 않는 단어장 타입입니다."),
    WORD_STORAGE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "W105", "해당 단어장에 대한 권한이 없습니다."),

    // Category
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "C101", "존재하지 않는 카테고리명입니다."),

    // Word
    WORD_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "W201", "단어(영)가 너무 깁니다."),
    WORD_DESCRIPTION_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "W202", "단어(영) 설명이 너무 깁니다."),
    WORD_NOT_FOUND(HttpStatus.BAD_REQUEST, "W203", "단어가 존재하지 않습니다."),

    // Meaning
    MEANING_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "M001", "단어(뜻)가 너무 깁니다."),

    // Pronounciation
    PRONONCIATION_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "P001", "단어(품사)가 너무 깁니다."),

    HISTORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "H001", "히스토리가 존재하지 않습니다.");

    private HttpStatus status;
    private String code;
    private String message;


    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
