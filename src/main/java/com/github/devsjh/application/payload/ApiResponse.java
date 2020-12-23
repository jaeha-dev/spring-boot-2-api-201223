package com.github.devsjh.application.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse {

    private final int status;
    private final Object data;

    @Builder
    public ApiResponse(final int status, final Object data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse(final Object data) {
        this.status = 200;
        this.data = data;
    }
}