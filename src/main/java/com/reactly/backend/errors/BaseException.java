package com.reactly.backend.errors;

public class BaseException extends Exception {
    public ErrorCode errorCode = ErrorCode.E_OK;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage()+ ": " +message);
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.getMessage() + ": " + message, cause);
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public BaseException(String requestId, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseException(String message, String requestId, ErrorCode errorCode) {
        super(errorCode.getMessage() + ": " +message);
        this.errorCode = errorCode;
    }

    public BaseException(String message, String requestId, Throwable cause, ErrorCode errorCode) {
        super(errorCode.getMessage() + ": " + message, cause);
        this.errorCode = errorCode;
    }

    public BaseException(String requestId, Throwable cause, ErrorCode errorCode) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public BaseException(String message, String requestId, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BaseException(String message, String requestId, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(errorCode.getMessage()+": "+message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public String type() {
        return "BaseException";
    }

}