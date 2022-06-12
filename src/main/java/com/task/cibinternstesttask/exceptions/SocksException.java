package com.task.cibinternstesttask.exceptions;

public class SocksException extends RuntimeException {
    private SocksErrorCode errorCode;

    public SocksException(SocksErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public SocksErrorCode getError() {
        return errorCode;
    }
}
