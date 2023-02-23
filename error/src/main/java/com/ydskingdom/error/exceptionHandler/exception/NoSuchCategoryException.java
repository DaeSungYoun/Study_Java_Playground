package com.ydskingdom.error.exceptionHandler.exception;

public class NoSuchCategoryException extends RuntimeException {

    private static final String MESSAGE = "카테고리 정보가 없습니다.";

    public NoSuchCategoryException() {
        super(MESSAGE);
    }
}
