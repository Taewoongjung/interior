package com.interior.application.command.util.email.template;

public interface EmailTemplate {

    String getTitle();

    String getContent();

    String getTargetEmail();
}
