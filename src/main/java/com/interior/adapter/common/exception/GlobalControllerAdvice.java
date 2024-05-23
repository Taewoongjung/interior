package com.interior.adapter.common.exception;

import com.interior.adapter.outbound.alarm.AlarmService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final AlarmService alarmService;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {

        HashMap<String, String> info = getRequestContextInfo();

        String httpMethod = info.get("httpMethod");
        String apiPath = info.get("apiPath");

        alarmService.sendErrorAlarm("[ " + httpMethod + "] " + apiPath, e.getMessage());

        return ResponseEntity.badRequest().body(new ErrorResponse(0, e.getMessage()));
    }

    private HashMap<String, String> getRequestContextInfo() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String httpMethod = "null";
        String apiPath = "null";

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            httpMethod = request.getMethod();
            apiPath = request.getServletPath();
        }

        HashMap<String, String> response = new HashMap<>();
        response.put("httpMethod", httpMethod);
        response.put("apiPath", apiPath);

        return response;
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(final InvalidInputException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getCode(), e.getMessage()));
    }
}
