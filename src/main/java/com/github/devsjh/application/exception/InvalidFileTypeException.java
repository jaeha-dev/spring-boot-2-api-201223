package com.github.devsjh.application.exception;

// JSON 파일을 등록하지 않을 때
public class InvalidFileTypeException extends BusinessException {

    public InvalidFileTypeException() {
        super(ErrorType.INVALID_FILE_TYPE);
    }
}