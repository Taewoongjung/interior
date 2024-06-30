package com.interior.application.commands.util.email.template;

public interface EmailTemplate {

    String getTitle();

    String getContent();

    String getTargetEmail();
}
