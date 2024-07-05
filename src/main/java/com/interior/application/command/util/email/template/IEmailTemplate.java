package com.interior.application.command.util.email.template;

public interface IEmailTemplate {

    String getTitle();

    String getContent();

    String getTargetEmail();
}
