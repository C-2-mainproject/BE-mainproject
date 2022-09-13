package com.wolves.mainproject.dto.WordStorageDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public enum WordStorageType {
    OFFICIAL("official"),
    PRIVATE("private"),
    PUBLIC("public");

    private String type;
}
