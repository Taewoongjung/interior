package com.interior.adapter.outbound.jpa.repository.alimtalk.kakaomsgresult;

import static com.interior.util.converter.jpa.alimtalk.kakao.KakaoMsgResultEntityConverter.kakaoMsgResultToEntity;
import static com.interior.util.converter.jpa.business.BusinessEntityConverter.businessThirdPartyMessageToEntity;

import com.interior.adapter.outbound.jpa.entity.alimtalk.KakaoMsgResultEntity;
import com.interior.adapter.outbound.jpa.entity.business.businessthirdpartymessage.BusinessThirdPartyMessageEntity;
import com.interior.adapter.outbound.jpa.repository.business.BusinessThirdPartyMessageJpaRepository;
import com.interior.domain.alimtalk.kakaomsgresult.KakaoMsgResult;
import com.interior.domain.alimtalk.kakaomsgresult.repository.KakaoMsgResultRepository;
import com.interior.domain.business.thirdpartymessage.BusinessThirdPartyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class KakaoMsgResultRepositoryAdapter implements KakaoMsgResultRepository {

    private final KakaoMsgResultJpaRepository kakaoMsgResultJpaRepository;
    private final BusinessThirdPartyMessageJpaRepository businessThirdPartyMessageJpaRepository;

    @Override
    @Transactional
    public Long save(final KakaoMsgResult kakaoMsgResult) {
        KakaoMsgResultEntity entity = kakaoMsgResultToEntity(kakaoMsgResult);
        KakaoMsgResultEntity result = kakaoMsgResultJpaRepository.save(entity);

        return result.getId();
    }

    @Override
    @Transactional
    public void createThirdPartyMessageSendLog(
            final BusinessThirdPartyMessage businessThirdPartyMessage) {

        BusinessThirdPartyMessageEntity entity = businessThirdPartyMessageToEntity(
                businessThirdPartyMessage);
        
        businessThirdPartyMessageJpaRepository.save(entity);
    }
}
