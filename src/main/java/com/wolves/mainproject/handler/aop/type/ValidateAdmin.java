package com.wolves.mainproject.handler.aop.type;

import com.wolves.mainproject.config.auth.PrincipalDetails;
import com.wolves.mainproject.domain.user.User;
import com.wolves.mainproject.domain.user.UserRepository;
import com.wolves.mainproject.exception.Common.CommonAccessDeniedException;
import com.wolves.mainproject.type.RoleType;
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
            throw new CommonAccessDeniedException();
    }
}
