package com.interior.adapter.inbound.user.query;

import com.interior.adapter.inbound.user.webdto.GetUserEmailWebDtoV1;
import com.interior.adapter.inbound.user.webdto.LoadUserDto.LoadUserResDto;
import com.interior.application.readmodel.user.GetUserEmailQueryHandler;
import com.interior.application.readmodel.user.handlers.LoadUserByTokenCommandHandler;
import com.interior.application.readmodel.user.handlers.ValidationCheckOfEmailQueryHandler;
import com.interior.application.readmodel.user.handlers.ValidationCheckOfPhoneNumberQueryHandler;
import com.interior.application.readmodel.user.queries.GetUserEmailQuery;
import com.interior.application.readmodel.user.queries.ValidationCheckOfEmailQuery;
import com.interior.application.readmodel.user.queries.ValidationCheckOfPhoneNumberQuery;
import com.interior.domain.user.User;
import com.interior.domain.util.BoolType;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserQueryController {

    private final GetUserEmailQueryHandler getUserEmailQueryHandler;
    private final LoadUserByTokenCommandHandler loadUserByTokenCommandHandler;
    private final ValidationCheckOfEmailQueryHandler validationCheckOfEmailQueryHandler;
    private final ValidationCheckOfPhoneNumberQueryHandler validationCheckOfPhoneNumberQueryHandler;


    @GetMapping(value = "/api/me")
    public ResponseEntity<LoadUserResDto> validateUser(final HttpServletRequest request) {

        User foundUser = loadUserByTokenCommandHandler.handle(request.getHeader("authorization"));

        log.info("{} 고객님 접속 중", foundUser.getName());

        String authorities = foundUser.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new LoadUserResDto(
                        foundUser.getUsername(),
                        foundUser.getTel(),
                        foundUser.getName(),
                        authorities,
                        foundUser.getCompanyList().stream()
                                .filter(f -> f.getIsDeleted() == BoolType.F)
                                .collect(Collectors.toList())
                ));
    }

    @GetMapping(value = "/api/emails/validations")
    public ResponseEntity<Boolean> validateEmail(
            @RequestParam("targetEmail") final String targetEmail,
            @RequestParam("compNumber") final String compNumber
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(validationCheckOfEmailQueryHandler.handle(
                        new ValidationCheckOfEmailQuery(targetEmail, compNumber)));
    }

    @GetMapping(value = "/api/phones/validations")
    public ResponseEntity<Boolean> validatePhone(
            @RequestParam("targetPhoneNumber") final String targetPhoneNumber,
            @RequestParam("compNumber") final String compNumber
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(validationCheckOfPhoneNumberQueryHandler.handle(
                        new ValidationCheckOfPhoneNumberQuery(targetPhoneNumber, compNumber)));
    }

    @GetMapping(value = "/api/users/emails")
    public ResponseEntity<GetUserEmailWebDtoV1.Res> getUserEmail(
            @RequestParam("phoneNumber") final String phoneNumber
    ) {

        User user = getUserEmailQueryHandler.handle(new GetUserEmailQuery(phoneNumber));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetUserEmailWebDtoV1.Res(user.getMaskedEmail(), user.getCreatedAt()));
    }
}
