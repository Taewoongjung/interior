package com.interior.adapter.inbound.user.command;

import com.interior.adapter.inbound.user.webdto.RequestValidation;
import com.interior.adapter.inbound.user.webdto.SignUpDtoWebDtoV1;
import com.interior.application.command.user.commands.SendEmailValidationMailCommand;
import com.interior.application.command.user.commands.SendPhoneValidationSmsCommand;
import com.interior.application.command.user.commands.SignUpCommand;
import com.interior.application.command.user.handlers.SendEmailValidationMailCommandHandler;
import com.interior.application.command.user.handlers.SendPhoneValidationSmsCommandHandler;
import com.interior.application.command.user.handlers.SignUpCommandHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserCommandController {

    private final SignUpCommandHandler signUpCommandHandler;
    private final SendEmailValidationMailCommandHandler sendEmailValidationMailCommandHandler;
    private final SendPhoneValidationSmsCommandHandler sendPhoneValidationSmsCommandHandler;

    @PostMapping(value = "/api/signup")
    public ResponseEntity<SignUpDtoWebDtoV1.Res> signup(
            final @Valid @RequestBody SignUpDtoWebDtoV1.Req req) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SignUpDtoWebDtoV1.Res(
                        true,
                        signUpCommandHandler.handle(
                                new SignUpCommand(
                                        req.name(),
                                        req.password(),
                                        req.email(),
                                        req.tel(),
                                        req.role()
                                )
                        )));
    }

    @PostMapping(value = "/api/emails/validations")
    public ResponseEntity<Boolean> requestValidationEmail(
            @RequestBody final RequestValidation.EmailValidationReq req
    ) throws Exception {

        sendEmailValidationMailCommandHandler.handle(
                new SendEmailValidationMailCommand(req.targetEmail()));

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping(value = "/api/phones/validations")
    public ResponseEntity<Boolean> requestValidationPhone(
            @RequestBody final RequestValidation.PhoneValidationReq req
    ) throws Exception {

        sendPhoneValidationSmsCommandHandler.handle(
                new SendPhoneValidationSmsCommand(req.targetPhoneNumber(), req.validationType()));

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
