package com.board.exception;

import lombok.Getter;

public enum ExceptionCode {
    INVALID_MEMBER(403, "Invalid Member, Cannot Fetch The Post"),
    POST_NOT_FOUND(404, "Post Not Found"),
    MEMBER_NOT_FOUND(404, "Member Not Found"),
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