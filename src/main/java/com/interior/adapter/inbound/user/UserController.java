package com.interior.adapter.inbound.user;

import com.interior.adapter.inbound.user.webdto.LoadUserDto.LoadUserResDto;
import com.interior.adapter.inbound.user.webdto.RequestValidationEmail;
import com.interior.adapter.inbound.user.webdto.SignUpDto;
import com.interior.adapter.inbound.user.webdto.SignUpDto.SignUpResDto;
import com.interior.application.command.user.UserCommandService;
import com.interior.application.query.user.UserQueryService;
import com.interior.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @PostMapping(value = "/api/signup")
    public ResponseEntity<SignUpResDto> signup(
            final @Valid @RequestBody SignUpDto.SignUpReqDto req) {

        return ResponseEntity.status(HttpStatus.OK).body(userCommandService.signUp(req));
    }

    @GetMapping(value = "/api/me")
    public ResponseEntity<LoadUserResDto> validateUser(final HttpServletRequest request) {

        User foundUser = userQueryService.loadUserByToken(request.getHeader("authorization"));

        log.info("{} 고객님 접속 중", foundUser.getName());
        log.debug("{} 고객님 접속 중(debug)", foundUser.getName());

        String authorities = foundUser.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new LoadUserResDto(
                        foundUser.getUsername(),
                        foundUser.getTel(),
                        foundUser.getName(),
                        authorities,
                        foundUser.getCompanyList()));
    }

    @PostMapping(value = "/api/emails/validations")
    public ResponseEntity<Boolean> requestValidationEmail(
            @RequestBody final RequestValidationEmail.Req req
    ) throws Exception {

        userCommandService.sendEmailValidationMail(req.targetEmail());

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @GetMapping(value = "/api/emails/validations")
    public ResponseEntity<Boolean> validateEmail(
            @RequestParam("targetEmail") final String targetEmail,
            @RequestParam("compNumber") final String compNumber
    ) throws Exception {

        return ResponseEntity.status(HttpStatus.OK)
                .body(userQueryService.validationCheckOfEmail(targetEmail, compNumber));
    }
}
