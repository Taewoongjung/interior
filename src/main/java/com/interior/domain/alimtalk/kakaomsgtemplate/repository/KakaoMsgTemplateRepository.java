package com.interior.domain.alimtalk.kakaomsgtemplate.repository;

import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplate;
import java.util.List;

public interface KakaoMsgTemplateRepository {

    void syncToTemplateRegistered(final List<KakaoMsgTemplate> target);

    KakaoMsgTemplate findByTemplateCode(final String templateCode);
}
