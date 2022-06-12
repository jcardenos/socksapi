package com.task.cibinternstesttask.exceptions;

import org.springframework.http.HttpStatus;

public enum SocksErrorCode {
    INCORRECT_PARAMS(HttpStatus.BAD_REQUEST),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus httpStatus;

    SocksErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
