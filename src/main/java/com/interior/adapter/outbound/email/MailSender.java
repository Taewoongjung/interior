package com.interior.adapter.outbound.email;

import static com.interior.adapter.common.exception.ErrorType.UNABLE_TO_SEND_EMAIL;

import com.interior.application.command.util.email.template.IEmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class MailSender {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void sendEmail(final MimeMessage mail) throws Exception {

        try {
            javaMailSender.send(mail);
        } catch (RuntimeException e) {
            log.info("MailService.sendEmail exception occur toEmail: " +
                    "title: {}, text: {}", mail.getSubject(), mail.getContent());
            throw new Exception(UNABLE_TO_SEND_EMAIL.getMessage());
        }
    }

    public MimeMessage createMail(final IEmailTemplate template) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(username);
            message.setRecipients(MimeMessage.RecipientType.TO, template.getTargetEmail());
            message.setSubject(template.getTitle());
            message.setText(template.getContent(), "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }
}
