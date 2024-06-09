package com.interior.application.command.util.email.template;

import lombok.Getter;

@Getter
public class EmailValidationCheckMail implements EmailTemplate {

    private final String title;
    private final String content;
    private final String targetEmail;

    private EmailValidationCheckMail(
            final String title,
            final String content,
            final String targetEmail
    ) {

        this.title = title;
        this.content = content;
        this.targetEmail = targetEmail;
    }

    public static EmailValidationCheckMail of(final String targetEmail, final int validationNum) {
        String title = "[인테리어 정(鄭)] 이메일 인증";

        String content = "";
        content += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
        content += "<h1>" + validationNum + "</h1>";
        content += "<h3>" + "감사합니다." + "</h3>";

        return new EmailValidationCheckMail(title, content, targetEmail);
    }
}
