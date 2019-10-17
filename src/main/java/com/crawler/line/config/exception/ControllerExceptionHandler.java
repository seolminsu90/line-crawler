package com.crawler.line.config.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crawler.line.config.domain.ApiResponse;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserLoginException.class)
    protected ApiResponse userLoginException(HttpServletRequest request, Exception e) {
        UserLoginException exception = (UserLoginException) e;
        return new ApiResponse(exception.getCode());
    }

    @ExceptionHandler(ScrapeException.class)
    protected ApiResponse scrapeException(HttpServletRequest request, Exception e) {
        ScrapeException exception = (ScrapeException) e;
        return new ApiResponse(exception.getCode());
    }

}
