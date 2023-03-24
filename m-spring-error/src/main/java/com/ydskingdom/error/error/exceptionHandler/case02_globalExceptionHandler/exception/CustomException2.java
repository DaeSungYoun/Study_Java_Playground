package com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.exception;

import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.config.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException2 extends RuntimeException {
    private final ErrorCode errorCode;
}
