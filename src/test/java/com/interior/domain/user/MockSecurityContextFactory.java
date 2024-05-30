package com.interior.domain.user;

import com.interior.adapter.inbound.user.webdto.SignUpDto;
import com.interior.application.command.user.UserCommandService;
import com.interior.application.query.user.UserQueryService;
import com.interior.domain.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MockSecurityContextFactory implements WithSecurityContextFactory<MockUser> {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @Override
    public SecurityContext createSecurityContext(MockUser annotation) {

        List<Company> userCompanyList = new ArrayList<>();

        for (int i = 0; i < annotation.userCompanyCount(); i++) {
            userCompanyList.add(Company.of(
                            (long) i,
                            "TW",
                            "01000",
                            10L,
                            "군자로 43",
                            "809호",
                            "0132104912908451209",
                            "01012345678",
                            LocalDateTime.of(2024, 5, 19, 23, 30),
                            LocalDateTime.of(2024, 5, 19, 23, 30)
                    )
            );
        }

        User mockUser = User.of(
                null,
                annotation.name(),
                annotation.email(),
                annotation.password(),
                annotation.tel(),
                annotation.userRole(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                userCompanyList
        );

        userCommandService.signUp(new SignUpDto.SignUpReqDto(
                mockUser.getName(),
                mockUser.getPassword(),
                mockUser.getEmail(),
                mockUser.getTel(),
                mockUser.getUserRole()
        ));

        UserDetails principal = userQueryService.loadUserByUsername(mockUser.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal
                , principal.getPassword()
                , principal.getAuthorities()
        );

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        return securityContext;
    }
}
