package com.ydskingdom.error.exceptionHandler.model;

import javax.validation.constraints.NotNull;

public class User {

    @NotNull
    private String name;

    @NotNull
    private int age;
}
