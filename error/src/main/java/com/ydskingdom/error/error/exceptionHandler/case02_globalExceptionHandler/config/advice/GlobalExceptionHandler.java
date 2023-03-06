package com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.config.advice;

import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.exception.BadRequestParameterException;
import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.dto.ErrorResponse;
import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.exception.CustomException2;
import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.exception.CustomException3;
import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.exception.NoSuchCategoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.config.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CustomException3.class})
    protected ResponseEntity<ErrorResponse> handleCustomException3() {
        log.error("===============>> handleCustomException3");
        return ErrorResponse.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = { CustomException2.class })
    protected ResponseEntity<ErrorResponse> handleCustomException2(CustomException2 e) {
        log.error("===============>> handleCustomException2", e);
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(NoSuchCategoryException.class)
    public ResponseEntity<ErrorResponse> handlerNoSuchCategoryException(NoSuchCategoryException e) {
        log.error("===============>> handlerNoSuchCategoryException", e);
        return ErrorResponse.toResponseEntity(NO_SUCH_CATEGORY_ELEMENT);
    }

    @ExceptionHandler(BadRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handlerBadRequestParameterException(BadRequestParameterException e) {
        log.error("===============>> handlerBadRequestParameterException", e);
        return ErrorResponse.toResponseEntity(INVALID_REQUEST_PARAMS);
    }
}
