package com.ydskingdom.error.exceptionHandler.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class ErrorResponse {
    private final int code;
    private final String msg;
    private final List<String> errorDetails;
    private final String responseTime;

    public ErrorResponse(int code, String msg, List<String> errorDetails) {
        this.code = code;
        this.msg = msg;
        this.errorDetails = errorDetails;
        this.responseTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
