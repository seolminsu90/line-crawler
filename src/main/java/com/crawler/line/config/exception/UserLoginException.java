package com.crawler.line.config.exception;

import com.crawler.line.config.domain.ApiResponseCode;

public class UserLoginException extends RuntimeException {
    private ApiResponseCode code;

    public UserLoginException(ApiResponseCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ApiResponseCode getCode() {
        return this.code;
    }
}