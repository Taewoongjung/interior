package com.interior.adapter.inbound.user.webdto;

public class RequestValidation {

    public record EmailValidationReq(String targetEmail) {

    }

    public record PhoneValidationReq(String targetPhoneNumber) {
        
    }
}
