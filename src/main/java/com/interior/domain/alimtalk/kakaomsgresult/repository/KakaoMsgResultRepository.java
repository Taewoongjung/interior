package com.interior.domain.alimtalk.kakaomsgresult.repository;

import com.interior.domain.alimtalk.kakaomsgresult.KakaoMsgResult;
import com.interior.domain.business.thirdpartymessage.BusinessThirdPartyMessage;

import java.util.List;

public interface KakaoMsgResultRepository {

    Long save(final KakaoMsgResult kakaoMsgResult);

    void createThirdPartyMessageSendLog(final BusinessThirdPartyMessage businessThirdPartyMessage);

    List<KakaoMsgResult> getAlimtalkHistory(final Long businessId);
}
