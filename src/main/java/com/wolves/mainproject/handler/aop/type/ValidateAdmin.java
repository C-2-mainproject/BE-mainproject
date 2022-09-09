package com.wolves.mainproject.handler.aop.type;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.role.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidateAdmin implements ValidateType{
    private final UserRepository userRepository;

    @Override
    public void validate(PrincipalDetails principalDetails) {
        User user = userRepository.findByUsername(principalDetails.getUsername()).orElseThrow();

        if (!user.getRole().equals(RoleType.ROLE_ADMIN))
            throw new RuntimeException("권한이 없습니다.");
    }
}
