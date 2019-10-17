package com.crawler.line.config.domain;

public enum ApiResponseCode {
    OK("0000", "요청 성공"), FAIL("1111", "요청 실패"), USER_AUTH_FAIL("0100", "인증 실패"), TOKEN_ERROR("0200", "토큰 오류"),
    USER_EXISTS("0101", "이미 있는 유저"), USER_PWD_NOTMATCH("0102", "패스워드 불일치"), USER_LOGIN_ERROR("0103", "아이디 또는 패스워드 오류"),
    USER_NOTFOUND("0104", "없는 유저입니다"), SCRAPE_ERROR("0300", "크롤링 오류");

    private String value;
    private String message;

    ApiResponseCode(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
