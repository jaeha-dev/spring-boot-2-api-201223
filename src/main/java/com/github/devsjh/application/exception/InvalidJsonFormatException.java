package com.github.devsjh.application.exception;

// JSON 파일을 등록했으나 형식이 맞지 않을 때
public class InvalidJsonFormatException extends BusinessException {

    public InvalidJsonFormatException() {
        super(ErrorType.INVALID_JSON_FORMAT);
    }
}