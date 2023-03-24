package com.ydskingdom.error.error.responseStatus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY, reason = "Bad Gateway")
public class BadGatewayException extends RuntimeException{
}
