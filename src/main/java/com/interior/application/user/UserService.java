package com.interior.application.user;

import static com.interior.adapter.common.exception.ErrorType.INVALID_SIGNUP_REQUEST_DUPLICATE_EMAIL;
import static com.interior.util.CheckUtil.check;

import com.interior.application.user.dto.LogInDto.LogInReqDto;
import com.interior.application.user.dto.LogInDto.LogInResDto;
import com.interior.application.user.dto.SignUpDto.SignUpReqDto;
import com.interior.application.user.dto.SignUpDto.SignUpResDto;
import com.interior.domain.auth.JwtToken;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Transactional
    public SignUpResDto signUp(final SignUpReqDto req) {

        userRepository.checkIfExistUserByEmail(req.email());

        User user = User.of(
            req.name(), req.email(), bCryptPasswordEncoder.encode(req.password()), req.tel(), req.role(),
            LocalDateTime.now(), LocalDateTime.now());

        User result = userRepository.save(user);

        return new SignUpResDto(result != null, result.getName());
    }
}
