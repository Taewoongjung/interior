package com.interior.adapter.outbound.jpa.repository.alimtalk.kakaomsgtemplate;

import com.interior.adapter.outbound.jpa.entity.alimtalk.KakaoMsgTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoMsgTemplateJpaRepository extends JpaRepository<KakaoMsgTemplateEntity, Long> {

    KakaoMsgTemplateEntity findKakaoMsgTemplateEntityByTemplateCode(final String templateCode);
}
