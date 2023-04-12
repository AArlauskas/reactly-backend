package com.reactly.backend.controllers;

import com.reactly.backend.errors.BaseException;
import com.reactly.backend.errors.ErrorCode;
import com.reactly.backend.errors.FailedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackageClasses = DefaultExceptionHandler.class)
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    FailedResponse handleControllerException(HttpServletRequest request, Exception ex)
    {

        log.error("Error handling request", ex);

        return new FailedResponse(ex.getClass().getSimpleName() + ":" + ex.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    ResponseEntity<FailedResponse> handleControllerException(HttpServletRequest request, BaseException ex) {
        FailedResponse resp = getResponseEntity(ex);

        // If exception has HTTP status annotation - reuse it
        ResponseStatus respStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (respStatus != null) {
            httpStatus = respStatus.code();
        } else if (ex.errorCode != null) {

            // Else get status by errorCode
            httpStatus = this.getHttpCodeFromErrorCode(ex.errorCode);
        }

        return new ResponseEntity<FailedResponse>(resp, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (ex instanceof BaseException)
            return new ResponseEntity<>(getResponseEntity((BaseException) ex), status);
        else {
            return new ResponseEntity<>(new FailedResponse(ex.getClass().getSimpleName() + ":" + ex.getMessage()),
                    status);
        }
    }

    private FailedResponse getResponseEntity(BaseException ex) {
        String msg = ex.getMessage();
        int code = -1;
        if (ex.errorCode != null) {
            code = ex.errorCode.getCode();
        }

        return new FailedResponse(code, msg);
    }

    private HttpStatus getHttpCodeFromErrorCode(ErrorCode code) {

        switch (code) {
            case BAD_PARAMETERS:
                return HttpStatus.BAD_REQUEST;
            case ENTRY_NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
