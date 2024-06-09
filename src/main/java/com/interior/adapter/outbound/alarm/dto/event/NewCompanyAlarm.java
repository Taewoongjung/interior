package com.interior.adapter.outbound.alarm.dto.event;

public record NewCompanyAlarm(String companyName,
                              String ownerName,
                              String email,
                              String tel,
                              String address) {

}
