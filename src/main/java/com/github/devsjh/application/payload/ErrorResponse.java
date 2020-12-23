package com.github.devsjh.application.payload;

import com.github.devsjh.application.exception.ErrorType;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int status;
    private final String message;

    private ErrorResponse(final ErrorType code) {
        this.status = code.getStatus();
        this.message = code.getMessage();
    }

    public static ErrorResponse of(final ErrorType type) {
        return new ErrorResponse(type);
    }
}