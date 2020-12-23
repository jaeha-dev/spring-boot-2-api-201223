package com.github.devsjh.application.exception;

// 데이터를 찾을 수 없을 때
public class NotFoundDataException extends BusinessException {

    public NotFoundDataException() {
        super(ErrorType.NOT_FOUND_DATA);
    }
}