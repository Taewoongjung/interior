package com.interior.application.commands.util.email;

import com.interior.adapter.outbound.cache.redis.email.CacheEmailValidationRedisRepository;
import com.interior.adapter.outbound.email.MailSender;
import com.interior.application.commands.util.email.template.EmailValidationCheckMail;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Async
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailSender mailSender;
    private final CacheEmailValidationRedisRepository cacheEmailValidationRedisRepository;

    @Transactional
    public void sendEmailValidationCheck(final String toEmail) throws Exception {

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
