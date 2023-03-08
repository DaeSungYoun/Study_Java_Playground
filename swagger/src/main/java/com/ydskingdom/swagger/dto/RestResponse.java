package com.ydskingdom.swagger.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
public class RestResponse<T> {
    private final int code;
    private final String msg;
    private final T result;
    private final String responseTime;

    public RestResponse(int code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
        this.responseTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
