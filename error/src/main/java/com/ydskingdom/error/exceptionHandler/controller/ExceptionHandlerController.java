package com.ydskingdom.error.exceptionHandler.controller;

import com.ydskingdom.error.exceptionHandler.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST) //@ResponseStatus에 설정된 HttpStatus.BAD_REQUEST로 인해 Http Status 400으로 내려감
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse exceptionHandler(IllegalArgumentException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    //@ResponseStatus 설정이 없어서 Status 200으로 내려감
    @ExceptionHandler(NullPointerException.class)
    public ErrorResponse exceptionHandler(NullPointerException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.OK) //@ResponseStatus에 설정된 HttpStatus.BAD_REQUEST로 인해 Http Status 200으로 내려감
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ErrorResponse exceptionHandler(IndexOutOfBoundsException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    /*
    Http Status : 400 OK
    {
        "code": 400,
        "msg": null,
        "errorDetails": null,
        "responseTime": "2023-02-23 15:50:04"
    }
     */
    @GetMapping("/response-status/test")
    public void test() {
        throw new IllegalArgumentException();
    }

    /*
    Http Status : 200 OK
    {
        "code": 400,
        "msg": null,
        "errorDetails": null,
        "responseTime": "2023-02-23 15:49:48"
    }
     */
    @GetMapping("/response-status/test1")
    public void test1() {
        throw new NullPointerException();
    }

    /*
    Http Status : 200 OK
    {
        "code": 400,
        "msg": null,
        "errorDetails": null,
        "responseTime": "2023-02-23 15:49:28"
    }
     */
    @GetMapping("/response-status/test2")
    public void test2() {
        throw new IndexOutOfBoundsException();
    }
}
