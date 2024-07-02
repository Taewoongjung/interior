package com.interior.domain.business.thirdpartymessage;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_RESULT_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_SENDER_ID;
import static com.interior.util.CheckUtil.require;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessThirdPartyMessage {

    private final Long id;

    private final Long businessId;

    private final Long senderId;

    private final Long kakaoMsgResultId;

    private BusinessThirdPartyMessage(
            final Long id,
            final Long businessId,
            final Long senderId,
            final Long kakaoMsgResultId
    ) {
        this.id = id;
        this.businessId = businessId;
        this.senderId = senderId;
        this.kakaoMsgResultId = kakaoMsgResultId;
    }

    public static BusinessThirdPartyMessage of(
            final Long businessId,
            final Long senderId,
            final Long kakaoMsgResultId
    ) {

        require(o -> businessId == null, businessId, EMPTY_BUSINESS_ID);
        require(o -> senderId == null, senderId, EMPTY_SENDER_ID);
        require(o -> kakaoMsgResultId == null, kakaoMsgResultId, EMPTY_RESULT_ID);

        return new BusinessThirdPartyMessage(null, businessId, senderId, kakaoMsgResultId);
    }
}
