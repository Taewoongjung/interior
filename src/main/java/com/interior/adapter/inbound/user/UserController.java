package com.interior.adapter.inbound.user;

import com.interior.application.security.UserDetailService;
import com.interior.application.user.UserService;
import com.interior.application.user.dto.LoadUserDto.LoadUserResDto;
import com.interior.application.user.dto.LogInDto.LogInReqDto;
import com.interior.application.user.dto.LogInDto.LogInResDto;
import com.interior.application.user.dto.SignUpDto.SignUpReqDto;
import com.interior.application.user.dto.SignUpDto.SignUpResDto;
import com.interior.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDetailService userDetailService;

    @PostMapping(value = "/api/signup")
    public ResponseEntity<SignUpResDto> signup(final @Valid @RequestBody SignUpReqDto req) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.signUp(req));
    }

    @GetMapping(value = "/api/me")
    public ResponseEntity<LoadUserResDto> validationUser(final HttpServletRequest request) {

        User foundUser = userDetailService.loadUserByToken(request.getHeader("authorization"));

        String authorities = foundUser.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new LoadUserResDto(
                        foundUser.getUsername(),
                        foundUser.getTel(),
                        foundUser.getName(),
                        authorities));
    }
}
