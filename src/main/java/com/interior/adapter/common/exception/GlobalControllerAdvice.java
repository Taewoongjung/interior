package com.interior.adapter.common.exception;

import com.interior.adapter.outbound.alarm.dto.event.ErrorAlarm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final ApplicationEventPublisher eventPublisher;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {

        HashMap<String, String> info = getRequestContextInfo();
        String httpMethod = info.get("httpMethod");
        String apiPath = info.get("apiPath");

        if (!"java.io.IOException: Broken pipe".equals(e.getMessage())) {
            eventPublisher.publishEvent(
                    new ErrorAlarm("[ " + httpMethod + "] " + apiPath, e.toString()));
        }

        return ResponseEntity.badRequest().body(new ErrorResponse(0, e.toString()));
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
