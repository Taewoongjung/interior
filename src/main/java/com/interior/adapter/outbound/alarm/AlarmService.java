package com.interior.adapter.outbound.alarm;

public interface AlarmService {

    void sendErrorAlarm(final String methodName, final String errorMsg);

    void sendNewUserAlarm(final String newUserName, final String email, final String tel);

    void sendNewCompanyAlarm(
            final String companyName,
            final String ownerName,
            final String email,
            final String tel,
            final String address
    );

    void sendNewBusinessAlarm(
            final String businessName,
            final String companyName,
            final String ownerName,
            final String email,
            final String tel
    );
}
