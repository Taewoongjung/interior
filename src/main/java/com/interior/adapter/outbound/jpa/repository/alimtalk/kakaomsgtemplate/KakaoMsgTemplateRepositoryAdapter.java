package com.interior.adapter.outbound.jpa.repository.alimtalk.kakaomsgtemplate;

import com.interior.adapter.outbound.jpa.entity.alimtalk.KakaoMsgTemplateEntity;
import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplate;
import com.interior.domain.alimtalk.kakaomsgtemplate.repository.KakaoMsgTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.interior.adapter.common.exception.ErrorType.NO_ALIMTALK_TEMPLATE;
import static com.interior.util.CheckUtil.check;
import static com.interior.util.converter.jpa.alimtalk.kakao.KakaoMsgTemplateEntityConverter.KakaoMsgTemplateToEntity;

@Repository
@RequiredArgsConstructor
public class KakaoMsgTemplateRepositoryAdapter implements KakaoMsgTemplateRepository {

    private final KakaoMsgTemplateJpaRepository kakaoMsgTemplateJpaRepository;

    @Override
    @Transactional
    public void syncToTemplateRegistered(final List<KakaoMsgTemplate> target) {

        List<KakaoMsgTemplateEntity> entityList = kakaoMsgTemplateJpaRepository.findAll();

        List<String> registeredTemplateCodeList = new ArrayList<>();
        target.forEach(e -> registeredTemplateCodeList.add(e.getTemplateCode()));

        List<KakaoMsgTemplateEntity> removeList = new ArrayList<>();

        for (String templateCode : registeredTemplateCodeList) {
            entityList.stream()
                    .filter(f -> !templateCode.equals(f.getTemplateCode()))
                    .forEach(removeList::add);
        }

        // 더이상 안쓰는 템플릿 제거
        kakaoMsgTemplateJpaRepository.deleteAllByIdInBatch(
                removeList.stream()
                        .map(KakaoMsgTemplateEntity::getId)
                        .collect(Collectors.toList())
        );


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

        check(entity == null, NO_ALIMTALK_TEMPLATE);

        return entity.toPojo();
    }
}
