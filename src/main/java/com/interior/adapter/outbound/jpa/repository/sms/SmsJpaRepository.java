package com.interior.adapter.outbound.jpa.repository.sms;

import com.interior.adapter.outbound.jpa.entity.sms.SmsSendResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsJpaRepository extends JpaRepository<SmsSendResultEntity, Long> {

}
