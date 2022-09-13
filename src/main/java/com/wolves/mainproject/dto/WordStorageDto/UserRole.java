package com.wolves.mainproject.dto.WordStorageDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN("ADMIN");

    private String role;
}
