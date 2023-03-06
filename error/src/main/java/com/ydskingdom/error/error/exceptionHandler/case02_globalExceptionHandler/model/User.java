package com.ydskingdom.error.error.exceptionHandler.case02_globalExceptionHandler.model;

import javax.validation.constraints.NotNull;

public class User {

    @NotNull
    private String name;

    @NotNull
    private int age;
}
