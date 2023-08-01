package com.board.exception;

import lombok.Getter;

public enum ExceptionCode {
    EMAIL_EXISTS(409, "Email Already Exists");

    @Getter
    private final int status;

    @Getter
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}