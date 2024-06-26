package com.interior.adapter.outbound.jpa.repository.alimtalk.kakaomsgtemplate;

import static com.interior.util.converter.jpa.alimtalk.kakao.KakaoMsgTemplateEntityConverter.KakaoMsgTemplateToEntity;

import com.interior.adapter.outbound.jpa.entity.alimtalk.KakaoMsgTemplateEntity;
import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplate;
import com.interior.domain.alimtalk.kakaomsgtemplate.repository.KakaoMsgTemplateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class KakaoMsgTemplateRepositoryAdapter implements KakaoMsgTemplateRepository {

    private final KakaoMsgTemplateJpaRepository kakaoMsgTemplateJpaRepository;

    @Override
    @Transactional
    public void syncToTemplateRegistered(final List<KakaoMsgTemplate> target) {

        List<KakaoMsgTemplateEntity> entityList = kakaoMsgTemplateJpaRepository.findAll();

        for (KakaoMsgTemplate req : target) {
            if (entityList.stream()
                    .noneMatch(f -> f.getTemplateCode().equals(req.getTemplateCode()))) {
                
                kakaoMsgTemplateJpaRepository.save(KakaoMsgTemplateToEntity(req));
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public KakaoMsgTemplate findByTemplateCode(final String templateCode) {

        KakaoMsgTemplateEntity entity = kakaoMsgTemplateJpaRepository.findKakaoMsgTemplateEntityByTemplateCode(
                templateCode);

        return entity.toPojo();
    }
}
