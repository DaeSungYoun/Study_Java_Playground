package com.ydskingdom.error.exceptionHandler.config.advice;

import com.ydskingdom.error.exceptionHandler.dto.ErrorResponse2;
import com.ydskingdom.error.exceptionHandler.exception.CustomException2;
import com.ydskingdom.error.exceptionHandler.exception.CustomException3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static com.ydskingdom.error.exceptionHandler.config.ErrorCode.DUPLICATE_RESOURCE;

/*
GlobalExceptionHandler.java
ErrorCode.java
ErrorResponse2.java
CustomException2.java
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { CustomException3.class})
    protected ResponseEntity<ErrorResponse2> handleCustomException3() {
        log.error("handleCustomException3 throw CustomException3 : {}", DUPLICATE_RESOURCE);
        return ErrorResponse2.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = { CustomException2.class })
    protected ResponseEntity<ErrorResponse2> handleCustomException2(CustomException2 e) {
        log.error("handleCustomException throw CustomException2 : {}", e.getErrorCode());
        return ErrorResponse2.toResponseEntity(e.getErrorCode());
    }
}
