package com.ydskingdom.error.exceptionHandler.exception;

import com.ydskingdom.error.exceptionHandler.config.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException2 extends RuntimeException {
    private final ErrorCode errorCode;
}
