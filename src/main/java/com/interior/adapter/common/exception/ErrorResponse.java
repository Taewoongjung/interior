package com.interior.adapter.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ErrorResponse {

    private int errorCode;
    private String message;
}
