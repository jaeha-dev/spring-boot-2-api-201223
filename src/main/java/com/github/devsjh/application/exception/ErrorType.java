package com.github.devsjh.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST.value(), "Invalid File Type"),
    INVALID_JSON_FORMAT(HttpStatus.BAD_REQUEST.value(), "Invalid JSON Format"),
    NOT_FOUND_DATA(HttpStatus.NOT_FOUND.value(), "Not Found Data");

    private final int status;
    private final String message;

    ErrorType(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}