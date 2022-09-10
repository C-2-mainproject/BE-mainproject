package com.wolves.mainproject.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserAdviceDto {

    private String title;
    private String category;
    private String content;
    private String evidenceImage;
    private Boolean personalConsent;
}
