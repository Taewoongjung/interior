package com.interior.helper.spy;

import static alimtalk.kakaomsgresult.KakaoMsgResultFixture.getBusinessThirdPartyMessage;
import static alimtalk.kakaomsgresult.KakaoMsgResultFixture.getKakaoMsgResultList;

import com.interior.domain.alimtalk.kakaomsgresult.KakaoMsgResult;
import com.interior.domain.alimtalk.kakaomsgresult.repository.KakaoMsgResultRepository;
import com.interior.domain.business.thirdpartymessage.BusinessThirdPartyMessage;
import java.util.List;

public class KakaoMsgResultRepositorySpy implements KakaoMsgResultRepository {

    @Override
    public Long save(KakaoMsgResult kakaoMsgResult) {
        return null;
    }

    @Override
    public void createThirdPartyMessageSendLog(
            BusinessThirdPartyMessage businessThirdPartyMessage) {

    }

    @Override
    public List<KakaoMsgResult> getAlimtalkHistory(Long businessId) {

        List<BusinessThirdPartyMessage> businessThirdPartyMessageList = getBusinessThirdPartyMessage();

        List<BusinessThirdPartyMessage> fillteredBusinessThirdPartyMsgList = businessThirdPartyMessageList.stream()
                .filter(f -> businessId.equals(f.getBusinessId()))
                .toList();

        List<Long> listOfKakaoMsgResultId = fillteredBusinessThirdPartyMsgList.stream()
                .map(BusinessThirdPartyMessage::getKakaoMsgResultId)
                .toList();

        List<KakaoMsgResult> msgResults = getKakaoMsgResultList();

        return msgResults.stream().filter(f -> listOfKakaoMsgResultId.contains(f.getId())).toList();
    }
}
