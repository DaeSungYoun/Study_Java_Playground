package com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.dto;

import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.config.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final List<String> errorDetails;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .errorDetails(null)
                        .code(errorCode.name())
                        .message(errorCode.getDetail())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode, List<String> errorDetails) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .errorDetails(errorDetails)
                        .code(errorCode.name())
                        .message(errorCode.getDetail())
                        .build()
                );
    }
}
