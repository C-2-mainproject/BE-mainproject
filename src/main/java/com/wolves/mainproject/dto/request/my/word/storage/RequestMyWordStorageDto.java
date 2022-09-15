package com.wolves.mainproject.dto.request.my.word.storage;

import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.word.storage.WordStorage;
import com.wolves.mainproject.domain.word.storage.category.WordStorageCategory;
import com.wolves.mainproject.exception.ErrorCode;
import com.wolves.mainproject.handler.aop.annotation.domain.LengthValidation;
import com.wolves.mainproject.type.StatusType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestMyWordStorageDto {
    @LengthValidation(length = 40, exception = ErrorCode.WORD_STORAGE_TITLE_TOO_LARGE)
    private String title;
    private String category;
    @LengthValidation(length = 255, exception = ErrorCode.WORD_STORAGE_DESCRIPTION_TOO_LARGE)
    private String description;
    private boolean status;
    private String type;

    public WordStorage toWordStorage(WordStorageCategory category, User user){
        return WordStorage.builder()
                .title(title)
                .description(description)
                .wordStorageCategory(category)
                .user(user)
                .status(StatusType.findByBoolean(status))
                .build();
    }
}
