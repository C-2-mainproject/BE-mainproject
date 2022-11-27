package com.wolves.mainproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllAdminNoteResponseDto {
    private Long id;
    private String title;
    private String content;

}
