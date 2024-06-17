package com.interior.domain.sms;

import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SmsSendResult {

    private Long id;

    private BoolType isSuccess;

    private String from;

    private String to;

    private LocalDateTime createdAt;

    private SmsSendResult(
            final Long id,
            final BoolType isSuccess,
            final String from,
            final String to,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.isSuccess = isSuccess;
        this.from = from;
        this.to = to;
        this.createdAt = createdAt;
    }

    // 생성
    public static SmsSendResult of(
            final BoolType isSuccess,
            final String from,
            final String to
    ) {
        return new SmsSendResult(null, isSuccess, from, to, null);
    }

    // 조회
    public static SmsSendResult of(
            final Long id,
            final BoolType isSuccess,
            final String from,
            final String to,
            final LocalDateTime createdAt
    ) {
        return new SmsSendResult(id, isSuccess, from, to, createdAt);
    }
}
