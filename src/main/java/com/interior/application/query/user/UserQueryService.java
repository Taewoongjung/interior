package com.interior.application.query.user;

import static com.interior.adapter.common.exception.ErrorType.EXPIRED_ACCESS_TOKEN;
import static com.interior.util.CheckUtil.check;

import com.interior.adapter.config.security.jwt.JWTUtil;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            log.info("찾을 수 없는 {} 이메일입니다.", email);
            return user;
        }
        return null;
    }

    public User loadUserByToken(final String reqToken) throws UsernameNotFoundException {

        String token = jwtUtil.getTokenWithoutBearer(reqToken);

        log.info("isExpired =" + jwtUtil.isExpired(token));

        check(jwtUtil.isExpired(token), EXPIRED_ACCESS_TOKEN);

        User user = userRepository.findByEmail(jwtUtil.getEmail(token));

        if (user != null) {
            return user;
        }
        return null;
    }
}
