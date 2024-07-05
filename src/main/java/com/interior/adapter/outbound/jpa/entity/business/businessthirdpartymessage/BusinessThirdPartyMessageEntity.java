package com.interior.adapter.outbound.jpa.entity.business.businessthirdpartymessage;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_RESULT_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_SENDER_ID;
import static com.interior.util.CheckUtil.require;

import com.interior.domain.business.thirdpartymessage.BusinessThirdPartyMessage;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "business_third_party_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessThirdPartyMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long businessId;

    private Long senderId;

    private Long kakaoMsgResultId;

    private BusinessThirdPartyMessageEntity(
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

    public static BusinessThirdPartyMessageEntity of(
            final Long id,
            final Long businessId,
            final Long senderId,
            final Long kakaoMsgResultId
    ) {

        require(o -> businessId == null, businessId, EMPTY_BUSINESS_ID);
        require(o -> senderId == null, senderId, EMPTY_SENDER_ID);
        require(o -> kakaoMsgResultId == null, kakaoMsgResultId, EMPTY_RESULT_ID);

        return new BusinessThirdPartyMessageEntity(id, businessId, senderId, kakaoMsgResultId);
    }

    public BusinessThirdPartyMessage toPojo() {
        return BusinessThirdPartyMessage.of(
                getBusinessId(),
                getSenderId(),
                getKakaoMsgResultId()
        );
    }
}
