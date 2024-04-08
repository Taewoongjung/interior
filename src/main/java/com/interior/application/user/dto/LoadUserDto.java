package com.interior.application.user.dto;

public class LoadUserDto {

    public record LoadUserResDto(String email, String tel, String name, String role) { }
}
