package com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.controller;

import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.config.ErrorCode;
import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.dto.ErrorResponse;
import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.exception.BadRequestParameterException;
import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.exception.CustomException2;
import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.exception.CustomException3;
import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.exception.NoSuchCategoryException;
import com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.config.ErrorCode.INVALID_REQUEST_PARAMS;

@Slf4j
@RestController
public class GlobalExceptionHandlerController {

    //@Valid에서 터지는 Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("===============>> handlerMethodArgumentNotValidException", e);

        List<String> errorDetails = new ArrayList<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            errorDetails.add(fieldError.getField() + " : " + fieldError.getDefaultMessage());
        }

        return ErrorResponse.toResponseEntity(INVALID_REQUEST_PARAMS, errorDetails);
    }

    /*
    {
        "timestamp": "2023-03-06T17:36:50.766576",
        "status": 404,
        "error": "NOT_FOUND",
        "errorDetails": null,
        "code": "NO_SUCH_CATEGORY_ELEMENT",
        "message": "카테고리 정보가 없습니다"
    }
     */
    @GetMapping("/global-excepton/test")
    public void noSuchCategoryException() {
        throw new NoSuchCategoryException();
    }


    /*
    {
        "timestamp": "2023-03-06T17:37:08.292726",
        "status": 400,
        "error": "BAD_REQUEST",
        "errorDetails": null,
        "code": "INVALID_REQUEST_PARAMS",
        "message": "잘못된 요청 정보 입니다,"
    }
     */
    @GetMapping("/global-excepton/test1")
    public void badRequestParameterException() {
        throw new BadRequestParameterException("errororororor");
    }

    /*
    {
        "timestamp": "2023-03-06T17:37:34.809643",
        "status": 400,
        "error": "BAD_REQUEST",
        "errorDetails": [
            "name : 널이어서는 안됩니다"
        ],
        "code": "INVALID_REQUEST_PARAMS",
        "message": "잘못된 요청 정보 입니다,"
    }
     */
    @PostMapping("/global-excepton/test2")
    public void methodArgumentNotValidException(@RequestBody @Valid User user) {
    }

    /*
    {
        "timestamp": "2023-03-06T17:38:03.002025",
        "status": 400,
        "error": "BAD_REQUEST",
        "errorDetails": null,
        "code": "CANNOT_FOLLOW_MYSELF",
        "message": "자기 자신은 팔로우 할 수 없습니다"
    }
     */
    @GetMapping("/global-excepton/test3")
    public void test3() {
        throw new CustomException2(ErrorCode.CANNOT_FOLLOW_MYSELF);
    }

    /*
    {
        "timestamp": "2023-03-06T17:38:14.114041",
        "status": 409,
        "error": "CONFLICT",
        "errorDetails": null,
        "code": "DUPLICATE_RESOURCE",
        "message": "데이터가 이미 존재합니다"
    }
     */
    @GetMapping("/global-excepton/test4")
    public void test4() {
        throw new CustomException3();
    }
}
