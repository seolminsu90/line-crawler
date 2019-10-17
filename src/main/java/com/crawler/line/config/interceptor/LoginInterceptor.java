package com.crawler.line.config.interceptor;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.crawler.line.config.domain.ApiResponseCode;
import com.crawler.line.config.exception.UserLoginException;
import com.crawler.line.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        log.info("interceptor Authorization : {}", authorizationHeader);
        if (authorizationHeader == null) {
            throw new UserLoginException(ApiResponseCode.USER_AUTH_FAIL);
        } else {
            try {
                String[] authorization = authorizationHeader.split(" ");

                log.info("interceptor Authorization scheme : {}", authorization[0]);
                log.info("interceptor Authorization token : {}", authorization[1]);

                String loginId = JwtUtil.verifyToken(authorization[1]);
                request.setAttribute("id", loginId);
            } catch (Exception e) {
                throw new UserLoginException(ApiResponseCode.TOKEN_ERROR);
            }
            return true;
        }
    }
}
