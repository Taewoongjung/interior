package com.interior.adapter.inbound.schedule.query;

import com.interior.adapter.inbound.schedule.webdto.GetBusinessSchedulesWebDtoV1;
import com.interior.application.readmodel.schedule.handlers.GetBusinessScheduleByBusinessIdQueryHandler;
import com.interior.application.readmodel.schedule.queries.GetBusinessScheduleByBusinessIdQuery;
import com.interior.domain.schedule.BusinessSchedule;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class BusinessScheduleQueryController {

    private final GetBusinessScheduleByBusinessIdQueryHandler getBusinessScheduleByBusinessIdQueryHandler;


    @GetMapping(value = "/api/businesses/schedules")
    public ResponseEntity<List<BusinessSchedule>> getBusinessSchedules(
            final GetBusinessSchedulesWebDtoV1.Req req
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(getBusinessScheduleByBusinessIdQueryHandler.handle(
                        new GetBusinessScheduleByBusinessIdQuery(req.businessIds())));
    }
}
