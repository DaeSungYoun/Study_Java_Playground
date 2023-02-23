package com.ydskingdom.error.error.controller;

import com.ydskingdom.error.error.exception.BadGatewayException;
import com.ydskingdom.error.error.exception.ForbiddenException;
import com.ydskingdom.error.error.exception.PageNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebErrorController {

    @GetMapping("/test")
    public String test() {
        System.out.println("test");
        return "test";
    }

    @GetMapping("/exception/403")
    public String runtimeException() {
        throw new ForbiddenException();
    }

    @GetMapping("/exception/404")
    public String notFoundException() {
        throw new PageNotFoundException("not found page");
    }

    @GetMapping("/exception/502")
    public String badGatewayException() {
        throw new BadGatewayException();
    }
}
