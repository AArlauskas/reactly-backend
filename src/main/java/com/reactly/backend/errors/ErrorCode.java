package com.reactly.backend.errors;

public enum ErrorCode {
    E_OK(200, ""),
    EXPORT_FAILED_SERVER(1000, "Export failed due to server error. Please try again later or contact support"),
    BAD_PARAMETERS(2000, ""),
    UNAUTHORIZED(3000, "Unauthorized"),
    ENTRY_NOT_FOUND(4000, "Entry in a database was not found");



    private final int code;
    private final String message;
    ErrorCode(int code, String message) { this.code = code; this.message = message; }
    public int getCode() { return code; }
    public String getMessage() { return message; }
}
