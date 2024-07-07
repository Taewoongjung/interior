package com.interior.domain.alimtalk.kakaomsgresult;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interior.domain.alimtalk.AlimTalkMessageType;
import com.interior.domain.util.BoolType;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.interior.adapter.common.exception.ErrorType.*;
import static com.interior.util.CheckUtil.require;

@Getter
public class KakaoMsgResult {

    private final Long id;

    private final String templateName;

    private final String templateCode;

    private final String messageSubject;

    private final String message;

    private final AlimTalkMessageType messageType;

    private final String receiverPhone;

    private final String msgId;

    private final BoolType isSuccess;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    private KakaoMsgResult(
            final Long id,
            final String templateName,
            final String templateCode,
            final String messageSubject,
            final String message,
            final AlimTalkMessageType messageType,
            final String receiverPhone,
            final String msgId,
            final BoolType isSuccess,
            final LocalDateTime createdAt
    ) {

        this.id = id;
        this.templateName = templateName;
        this.templateCode = templateCode;
        this.messageSubject = messageSubject;
        this.message = message;
        this.messageType = messageType;
        this.receiverPhone = receiverPhone;
        this.msgId = msgId;
        this.isSuccess = isSuccess;
        this.createdAt = createdAt;
    }

    // 생성
    public static KakaoMsgResult of(
            final String templateName,
            final String templateCode,
            final String messageSubject,
            final String message,
            final AlimTalkMessageType messageType,
            final String receiverPhone,
            final String msgId,
            final BoolType isSuccess
    ) {

        require(o -> templateName == null, templateName, EMPTY_KAKAO_MSG_TEMPLATE_NAME);
        require(o -> templateCode == null, templateCode, EMPTY_KAKAO_MSG_TEMPLATE_CODE);
        require(o -> messageType == null, messageType, EMPTY_KAKAO_MSG_RESULT_TYPE);
        require(o -> receiverPhone == null, receiverPhone, EMPTY_KAKAO_MSG_RESULT_RECEIVER_PHONE);
        require(o -> isSuccess == null, isSuccess, EMPTY_IS_DELETED);

        return new KakaoMsgResult(
                null,
                templateName,
                templateCode,
                messageSubject,
                message,
                messageType,
                receiverPhone,
                msgId,
                isSuccess,
                LocalDateTime.now()
        );
    }

    // 조회
    public static KakaoMsgResult of(
            final Long id,
            final String templateName,
            final String templateCode,
            final String messageSubject,
            final String message,
            final AlimTalkMessageType messageType,
            final String receiverPhone,
            final String msgId,
            final BoolType isSuccess,
            final LocalDateTime createdAt
    ) {

        require(o -> templateName == null, templateName, EMPTY_KAKAO_MSG_TEMPLATE_NAME);
        require(o -> templateCode == null, templateCode, EMPTY_KAKAO_MSG_TEMPLATE_CODE);
        require(o -> messageType == null, messageType, EMPTY_KAKAO_MSG_RESULT_TYPE);
        require(o -> receiverPhone == null, receiverPhone, EMPTY_KAKAO_MSG_RESULT_RECEIVER_PHONE);
        require(o -> isSuccess == null, isSuccess, EMPTY_IS_DELETED);

        return new KakaoMsgResult(
                id,
                templateName,
                templateCode,
                messageSubject,
                message,
                messageType,
                receiverPhone,
                msgId,
                isSuccess,
                createdAt
        );
    }
}
