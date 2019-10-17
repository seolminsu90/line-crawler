package com.crawler.line.config.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private List<T> list;
    private String code;
    private String msg;

    public ApiResponse(ApiResponseCode code) {
        this.code = code.getValue();
        this.msg = code.getMessage();
    }

    public ApiResponse(T data, ApiResponseCode code) {
        this.data = data;
        this.code = code.getValue();
        this.msg = code.getMessage();
    }

    public ApiResponse(List<T> list, ApiResponseCode code) {
        this.list = list;
        this.code = code.getValue();
        this.msg = code.getMessage();
    }
}
