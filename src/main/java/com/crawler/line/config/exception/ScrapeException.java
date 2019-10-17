package com.crawler.line.config.exception;

import com.crawler.line.config.domain.ApiResponseCode;

public class ScrapeException extends RuntimeException {
    private ApiResponseCode code;

    public ScrapeException(ApiResponseCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ApiResponseCode getCode() {
        return this.code;
    }
}