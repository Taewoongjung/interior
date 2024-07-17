package com.interior.adapter.inbound.schedule.command;

import com.interior.adapter.inbound.schedule.webdto.CreateScheduleWebDtoV1;
import com.interior.application.command.schedule.handlers.CreateScheduleCommandHandler;
import com.interior.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class BusinessScheduleCommandController {

    private final CreateScheduleCommandHandler createScheduleCommandHandler;

    @PostMapping(value = "/api/businesses/schedules")
    public ResponseEntity<Boolean> createBusinessSchedule(
            @Valid @RequestBody CreateScheduleWebDtoV1.Req req,
            @AuthenticationPrincipal final User user
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(createScheduleCommandHandler.handle(req.toCommand(user.getId())));
    }

}
