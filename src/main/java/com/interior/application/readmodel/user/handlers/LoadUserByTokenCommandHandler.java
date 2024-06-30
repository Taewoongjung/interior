package com.interior.application.readmodel.user.handlers;

import static com.interior.adapter.common.exception.ErrorType.EXPIRED_ACCESS_TOKEN;
import static com.interior.util.CheckUtil.check;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.adapter.config.security.jwt.JWTUtil;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadUserByTokenCommandHandler implements ICommandHandler<String, User> {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public User handle(final String reqToken) {
        String token = jwtUtil.getTokenWithoutBearer(reqToken);

        check(jwtUtil.isExpired(token), EXPIRED_ACCESS_TOKEN);

        User user = userRepository.findByEmail(jwtUtil.getEmail(token));

        if (user != null) {
            return user;
        }

        return null;
    }
}
