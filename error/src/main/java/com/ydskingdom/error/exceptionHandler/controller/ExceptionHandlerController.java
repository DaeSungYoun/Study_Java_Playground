package com.ydskingdom.error.exceptionHandler.controller;

import com.ydskingdom.error.exceptionHandler.exception.BadRequestParameterException;
import com.ydskingdom.error.exceptionHandler.exception.NoSuchCategoryException;
import com.ydskingdom.error.exceptionHandler.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/exception")
@RestController
public class ExceptionHandlerController {

    @GetMapping("/no-such")
    public void noSuchCategoryException() {
        throw new NoSuchCategoryException();
    }

    @GetMapping("/bad-request")
    public void badRequestParameterException() {
        throw new BadRequestParameterException("errororororor");
    }

    @PostMapping("/method-not-valid")
    public void methodArgumentNotValidException(@RequestBody @Valid User user) {
    }
}
