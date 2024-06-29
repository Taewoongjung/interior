package com.interior.domain.business.thirdpartymessage;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessThirdPartyMessage {

    private Long id;

    private Long businessId;

    private Long senderId;

    private Long kakaoMsgResultId;

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
        return new BusinessThirdPartyMessage(null, businessId, senderId, kakaoMsgResultId);
    }
}
