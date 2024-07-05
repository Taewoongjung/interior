package com.interior.application.command.util.email;

import com.interior.abstraction.serviceutill.IThirdPartyValidationCheckSender;
import com.interior.adapter.outbound.cache.redis.email.CacheEmailValidationRedisRepository;
import com.interior.adapter.outbound.email.MailSender;
import com.interior.application.command.util.email.template.EmailValidationCheckMail;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Async
@Slf4j
@Service
@RequiredArgsConstructor
@Qualifier("EmailIUtilService")
public class EmailIUtilService implements IThirdPartyValidationCheckSender {

    private final MailSender mailSender;
    private final CacheEmailValidationRedisRepository cacheEmailValidationRedisRepository;

    @Override
    @Transactional
    public void sendValidationCheck(final String toEmail) throws Exception {

        int validationNumber = createNumber();

        cacheEmailValidationRedisRepository.makeBucketByKey(toEmail, validationNumber);

        MimeMessage mail = mailSender.createMail(
                EmailValidationCheckMail.of(toEmail, validationNumber));

        mailSender.sendEmail(mail);
    }

    private int createNumber() {
        return (int) (Math.random() * (90000)) + 100000; //(int) Math.random() * (최댓값-최소값+1) + 최소값
    }
}
