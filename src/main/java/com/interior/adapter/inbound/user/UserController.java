package com.interior.adapter.inbound.user;

import com.interior.application.user.UserService;
import com.interior.application.user.dto.LogInDto.LogInReqDto;
import com.interior.application.user.dto.LogInDto.LogInResDto;
import com.interior.application.user.dto.SignUpDto.SignUpReqDto;
import com.interior.application.user.dto.SignUpDto.SignUpResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/api/signup")
    public ResponseEntity<SignUpResDto> signup(final @Valid @RequestBody SignUpReqDto req) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.signUp(req));
    }

    @PostMapping(value = "/api/login")
    public ResponseEntity<LogInResDto> login(final @Valid @RequestBody LogInReqDto req) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.logIn(req));
    }
}
