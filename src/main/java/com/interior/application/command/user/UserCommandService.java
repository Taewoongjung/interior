package com.interior.application.command.user;

import com.interior.adapter.inbound.user.webdto.SignUpDto.SignUpReqDto;
import com.interior.adapter.inbound.user.webdto.SignUpDto.SignUpResDto;
import com.interior.adapter.outbound.alarm.AlarmService;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final AlarmService alarmService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public SignUpResDto signUp(final SignUpReqDto req) {

        userRepository.checkIfExistUserByEmail(req.email());

        User user = User.of(
                req.name(), req.email(), bCryptPasswordEncoder.encode(req.password()), req.tel(), req.role(),
                LocalDateTime.now(), LocalDateTime.now());

        User newUser = userRepository.save(user);

        if (newUser != null) {

            alarmService.sendNewUserAlarm(req.name(), req.email(), req.tel());

            return new SignUpResDto(true, newUser.getName());
        }

        return new SignUpResDto(false, null);
    }
}
