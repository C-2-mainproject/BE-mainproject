package com.wolves.mainproject.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateMyWordStorageStatusDto {
    private boolean status;
}
