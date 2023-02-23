package com.ydskingdom.error.exceptionHandler.controller;

import com.ydskingdom.error.exceptionHandler.config.ErrorCode;
import com.ydskingdom.error.exceptionHandler.exception.BadRequestParameterException;
import com.ydskingdom.error.exceptionHandler.exception.CustomException2;
import com.ydskingdom.error.exceptionHandler.exception.CustomException3;
import com.ydskingdom.error.exceptionHandler.exception.NoSuchCategoryException;
import com.ydskingdom.error.exceptionHandler.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
public class GlobalExceptionHandlerController {

    /*
    @RestControllerAdvice, @ExceptionHandler 적용

    {
        "code": 404,
        "msg": "카테고리 정보가 없습니다.",
        "errorDetails": null,
        "responseTime": "2023-02-23 15:15:48"
    }
     */
    @GetMapping("/global-excepton/test")
    public void noSuchCategoryException() {
        throw new NoSuchCategoryException();
    }


    /*
    @RestControllerAdvice, @ExceptionHandler 적용

    {
        "code": 400,
        "msg": "errororororor",
        "errorDetails": null,
        "responseTime": "2023-02-23 15:14:37"
    }
     */
    @GetMapping("/global-excepton/test1")
    public void badRequestParameterException() {
        throw new BadRequestParameterException("errororororor");
    }

    /*
    @RestControllerAdvice, @ExceptionHandler 적용

    {
        "code": 400,
        "msg": "잘못된 요청입니다.",
        "errorDetails": [
            "name : 널이어서는 안됩니다"
        ],
        "responseTime": "2023-02-23 15:14:13"
    }
     */
    @PostMapping("/global-excepton/test2")
    public void methodArgumentNotValidException(@RequestBody @Valid User user) {
    }

    /*
    @RestControllerAdvice, @ExceptionHandler 적용
    {
        "timestamp": "2023-02-23T17:59:05.908604",
        "status": 400,
        "error": "BAD_REQUEST",
        "code": "CANNOT_FOLLOW_MYSELF",
        "message": "자기 자신은 팔로우 할 수 없습니다"
    }
     */
    @GetMapping("/global-excepton/test3")
    public void test3() {
        throw new CustomException2(ErrorCode.CANNOT_FOLLOW_MYSELF);
    }

    /*
    @RestControllerAdvice, @ExceptionHandler 적용
    {
        "timestamp": "2023-02-23T17:59:17.164716",
        "status": 409,
        "error": "CONFLICT",
        "code": "DUPLICATE_RESOURCE",
        "message": "데이터가 이미 존재합니다"
    }
     */
    @GetMapping("/global-excepton/test4")
    public void test4() {
        throw new CustomException3();
    }
}
