package com.interior.application.readmodel.schedule.queryresponses;

import com.interior.adapter.inbound.schedule.webdto.GetBusinessSchedulesWebDtoV1;
import com.interior.adapter.inbound.schedule.webdto.GetBusinessSchedulesWebDtoV1.Res;
import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.BusinessScheduleAlarm;
import java.util.ArrayList;
import java.util.List;

public record GetBusinessScheduleByBusinessIdQueryResponse(
        List<BusinessSchedule> businessScheduleList,
        List<BusinessScheduleAlarm> businessScheduleAlarmList) {

    public List<GetBusinessSchedulesWebDtoV1.Res> convertToGetBusinessSchedulesWebDtoV1Res() {
        List<Res> result = new ArrayList<>();

        for (BusinessSchedule businessSchedule : this.businessScheduleList()) {

            BusinessScheduleAlarm businessScheduleAlarm = this.businessScheduleAlarmList()
                    .stream()
                    .filter(f -> businessSchedule.getId().equals(f.getBusinessScheduleId()))
                    .findFirst().orElse(null);

            result.add(new Res(
                    businessSchedule.getId(),
                    businessSchedule.getBusinessId(),
                    businessSchedule.getUserId(),
                    businessSchedule.getType(),
                    businessSchedule.getTitle(),
                    businessSchedule.getOrderingPlace(),
                    businessSchedule.getStartDate(),
                    businessSchedule.getEndDate(),
                    businessSchedule.getIsAlarmOn(),
                    businessSchedule.getIsDeleted(),
                    businessSchedule.getColorHexInfo(),
                    businessSchedule.getCreatedAt(),
                    businessScheduleAlarm != null ?
                            businessScheduleAlarm.getAlarmStartDate() : null,
                    businessScheduleAlarm != null ?
                            businessScheduleAlarm.getSelectedAlarmTime() : null
            ));
        }

        return result;
    }
}
