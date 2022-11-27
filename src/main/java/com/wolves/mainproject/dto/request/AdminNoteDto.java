package com.wolves.mainproject.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdminNoteDto {

    private String title;

    private String content;

}
