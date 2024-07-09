package com.interior.adapter.inbound.user.webdto;

public class ResetUserPasswordWebDtoV1 {

    public record Req(String email, String phoneNumber, String password) {

    }
}
