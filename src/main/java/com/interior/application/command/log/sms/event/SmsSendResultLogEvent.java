package com.interior.application.command.log.sms.event;

import java.util.HashMap;

public record SmsSendResultLogEvent(
        String senderPhoneNumber,
        String to,
        HashMap<String, String> msg,
        String platformType) {

}
