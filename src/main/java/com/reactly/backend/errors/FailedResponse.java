package com.reactly.backend.errors;

import lombok.Getter;
import lombok.Setter;

public class FailedResponse {

    @Getter @Setter
    private int errorCode;
    @Getter @Setter
    private String message;

    public FailedResponse() {}

    public FailedResponse(int errorCode) {
        this.errorCode = errorCode;
        message = "";
    }

    public FailedResponse(String message)
    {
        this.message = message;
    }

    public FailedResponse(int errorCode, String message)
    {
        this.errorCode = errorCode;
        this.message = message;
    }

}
