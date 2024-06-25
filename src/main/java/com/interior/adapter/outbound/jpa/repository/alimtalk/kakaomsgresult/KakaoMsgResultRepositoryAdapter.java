package com.interior.adapter.outbound.jpa.repository.alimtalk.kakaomsgresult;

import static com.interior.util.converter.jpa.alimtalk.kakao.KakaoMsgResultEntityConverter.kakaoMsgResultToEntity;

import com.interior.adapter.outbound.jpa.entity.alimtalk.KakaoMsgResultEntity;
import com.interior.domain.alimtalk.kakaomsgresult.KakaoMsgResult;
import com.interior.domain.alimtalk.kakaomsgresult.repository.KakaoMsgResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class KakaoMsgResultRepositoryAdapter implements KakaoMsgResultRepository {

    private final KakaoMsgResultJpaRepository kakaoMsgResultJpaRepository;

    @Override
    @Transactional
    public void save(final KakaoMsgResult kakaoMsgResult) {
        KakaoMsgResultEntity entity = kakaoMsgResultToEntity(kakaoMsgResult);
        kakaoMsgResultJpaRepository.save(entity);
    }
}
