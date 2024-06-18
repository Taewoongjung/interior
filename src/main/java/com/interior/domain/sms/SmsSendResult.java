package com.interior.domain.sms;

import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_SMS_SEND_RESULT;
import static com.interior.util.CheckUtil.check;

import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

@Slf4j
@Getter
public class SmsSendResult {

    private Long id;

    private BoolType isSuccess;

    private String type;

    private String sender;

    private String receiver;

    private String platformType;

    private String resultCode;

    private String msgId;

    private LocalDateTime createdAt;

    private SmsSendResult(
            final Long id,
            final BoolType isSuccess,
            final String type,
            final String sender,
            final String receiver,
            final String platformType,
            final String resultCode,
            final String msgId,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.isSuccess = isSuccess;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.platformType = platformType;
        this.resultCode = resultCode;
        this.msgId = msgId;
        this.createdAt = createdAt;
    }

    // 생성
    public static SmsSendResult of(
            final BoolType isSuccess,
            final String type,
            final String sender,
            final String receiver,
            final String platformType,
            final String resultCode,
            final String msgId
    ) {
        return new SmsSendResult(null, isSuccess, type, sender, receiver, platformType, resultCode,
                msgId,
                null);
    }

    public static SmsSendResult of(
            final String sender,
            final String receiver,
            final JSONObject json,
            final String platformType
    ) {

        SmsSendResult smsSendResult = null;

        try {
            if ("ALIGO".equals(platformType)) {
                if ("success".equals(json.get("message"))) {
                    smsSendResult = SmsSendResult.of(
                            BoolType.T,
                            json.get("msg_type").toString(),
                            sender,
                            receiver,
                            platformType,
                            json.get("result_code").toString(),
                            json.get("msg_id").toString()
                    );
                } else {
                    smsSendResult = SmsSendResult.of(
                            BoolType.F,
                            "",
                            sender,
                            receiver,
                            platformType,
                            json.get("result_code").toString(),
                            ""
                    );
                }
            }

        } catch (Exception e) {
            log.error("SmsSendResult 객체 생성 중 JSONObject 파싱 실패 : {}", e.getMessage());
        }

        check(smsSendResult == null, NOT_EXIST_SMS_SEND_RESULT);

        return smsSendResult;
    }

    // 조회
    public static SmsSendResult of(
            final Long id,
            final BoolType isSuccess,
            final String type,
            final String sender,
            final String receiver,
            final String platformType,
            final String resultCode,
            final String msgId,
            final LocalDateTime createdAt
    ) {
        return new SmsSendResult(id, isSuccess, type, sender, receiver, platformType, resultCode,
                msgId, createdAt);
    }
}
