package com.interior.adapter.config;

import com.interior.application.security.UserDetailService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class JwtAuthenticationProvider  implements AuthenticationProvider {

    private final UserDetailService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        String loginId = auth.getName();
        String password = (String) auth.getCredentials();

        UserDetails user = userDetailsService.loadUserByUsername(loginId);

        if(Objects.isNull(user)) {
            throw new BadCredentialsException("user id not found!");
        }
        else if (!this.passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("password is not matches");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginId,
                null,
                user.getAuthorities()
        );
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
