package com.ydskingdom.error.exceptionHandler.config.advice;

import com.ydskingdom.error.exceptionHandler.dto.ErrorResponse;
import com.ydskingdom.error.exceptionHandler.exception.BadRequestParameterException;
import com.ydskingdom.error.exceptionHandler.exception.NoSuchCategoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@org.springframework.web.bind.annotation.RestControllerAdvice(annotations = RestController.class)
public class RestControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchCategoryException.class)
    public ErrorResponse handlerNoSuchCategoryException(NoSuchCategoryException e) {
        log.error("===============>> handlerNoSuchCategoryException", e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse handlerNoSuchElementException(NoSuchElementException e) {
        log.error("===============>> handlerNoSuchCategoryException", e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), "카테고리 정보가 없습니다.", null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestParameterException.class)
    public ErrorResponse handlerBadRequestParameterException(BadRequestParameterException e) {
        log.error("===============>> handlerBadRequestParameterException", e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("===============>> handlerMethodArgumentNotValidException", e);

        List<String> errorDetails = new ArrayList<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            errorDetails.add(fieldError.getField() + " : " + fieldError.getDefaultMessage());
        }

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", errorDetails);
    }
}
