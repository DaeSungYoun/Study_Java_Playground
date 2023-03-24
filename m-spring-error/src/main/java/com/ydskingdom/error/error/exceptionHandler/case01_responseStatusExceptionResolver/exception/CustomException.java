package com.ydskingdom.error.error.exceptionHandler.case01_responseStatusExceptionResolver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Request Custom Exception")
public class CustomException extends RuntimeException{
}
