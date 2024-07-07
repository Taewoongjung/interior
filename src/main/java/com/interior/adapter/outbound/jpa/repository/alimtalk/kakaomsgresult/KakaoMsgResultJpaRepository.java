package com.interior.adapter.outbound.jpa.repository.alimtalk.kakaomsgresult;

import com.interior.adapter.outbound.jpa.entity.alimtalk.KakaoMsgResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KakaoMsgResultJpaRepository extends JpaRepository<KakaoMsgResultEntity, Long> {

    List<KakaoMsgResultEntity> findAllByIdIn(final List<Long> id);
}
