package com.interior.domain.alimtalk;

import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class KakaoMsgResult {

    private Long id;

    private String templateName;

    private String templateCode;

    private String messageSubject;

    private String message;

    private AlimTalkMessageType messageType;

    private String receiverPhone;

    private String msgId;

    private BoolType isSuccess;

    private LocalDateTime createdAt;

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
            final BoolType isSuccess,
            final LocalDateTime createdAt
    ) {

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
                createdAt
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
