package com.wolves.mainproject.dto.response;

import com.wolves.mainproject.domain.user.advice.UserAdvice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InquiryDto {
    private long id;
    private String title;
    private String content;

    private String  evidence_image;
    private LocalDateTime createAt;
    private boolean clear;

    public void fromUserAdvice (UserAdvice userAdvice){
        this.id = userAdvice.getId();
        this.title = userAdvice.getTitle();
        this.content = userAdvice.getContent();
        this.evidence_image = userAdvice.getEvidenceImage();
        this.createAt = userAdvice.getCreateAt();
        this.clear = userAdvice.isClear();
    }
    public InquiryDto(UserAdvice userAdvice){
        fromUserAdvice(userAdvice);
    }


}
