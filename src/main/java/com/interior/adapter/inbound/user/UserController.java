package com.interior.adapter.inbound.user;

import com.interior.application.user.UserService;
import com.interior.application.user.dto.SignUpDto.SignUpReqDto;
import com.interior.application.user.dto.SignUpDto.SignUpResDto;
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

    @PostMapping(value = "/signup")
    public ResponseEntity<SignUpResDto> signup(@RequestBody final SignUpReqDto req) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.signUp(req));
    }
}