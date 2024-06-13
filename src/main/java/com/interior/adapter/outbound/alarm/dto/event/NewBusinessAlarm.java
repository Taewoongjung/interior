package com.interior.adapter.outbound.alarm.dto.event;

public record NewBusinessAlarm(String businessName,
                               String companyName,
                               String ownerName,
                               String email,
                               String tel,
                               String businessAddress,
                               String bdgNumber) {

}
