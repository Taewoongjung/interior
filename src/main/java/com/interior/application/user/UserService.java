package com.interior.application.user;

import com.interior.application.user.dto.SignUpDto.SignUpReqDto;
import com.interior.application.user.dto.SignUpDto.SignUpResDto;
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

        User user = User.of(
            req.name(), req.email(), bCryptPasswordEncoder.encode(req.password()), req.tel(), req.role(),
            LocalDateTime.now(), LocalDateTime.now());

        User result = userRepository.save(user);

        return new SignUpResDto(result != null, result.getName());
    }
}
