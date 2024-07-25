package com.interior.application.readmodel.schedule.handlers;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.application.readmodel.schedule.queries.GetBusinessScheduleByBusinessIdQuery;
import com.interior.application.readmodel.schedule.queryresponses.GetBusinessScheduleByBusinessIdQueryResponse;
import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import com.interior.domain.schedule.repository.BusinessScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetBusinessScheduleByBusinessIdQueryHandler implements
        IQueryHandler<GetBusinessScheduleByBusinessIdQuery, GetBusinessScheduleByBusinessIdQueryResponse> {

    private final BusinessScheduleRepository businessScheduleRepository;


    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public GetBusinessScheduleByBusinessIdQueryResponse handle(
            final GetBusinessScheduleByBusinessIdQuery event) {

        List<BusinessSchedule> businessSchedules = businessScheduleRepository.findAllByBusinessId(
                event.businessIdList());

        List<BusinessScheduleAlarm> businessScheduleAlarmList = businessScheduleRepository.findAllScheduleAlarmByBusinessScheduleIdList(
                businessSchedules.stream()
                        .map(BusinessSchedule::getId).toList()
        );

        return new GetBusinessScheduleByBusinessIdQueryResponse(
                businessSchedules,
                businessScheduleAlarmList);
    }
}
