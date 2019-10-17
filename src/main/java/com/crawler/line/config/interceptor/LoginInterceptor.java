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
        Cookie[] cookies = request.getCookies();
        String[] authorization = null;
        if (cookies != null && cookies.length > 0) {
            Optional<String> cookie = readCookie(request, "Authorization");
            if (!cookie.isPresent()) {
                throw new UserLoginException(ApiResponseCode.USER_AUTH_FAIL);
            } else {
                try {
                    authorization = cookie.get().split(" ");
                } catch (Exception e) {
                    throw new UserLoginException(ApiResponseCode.TOKEN_ERROR);
                }

                log.debug("interceptor Authorization scheme : {}", authorization[0]);
                log.debug("interceptor Authorization token : {}", authorization[1]);

                String loginId = JwtUtil.verifyToken(authorization[1]);
                request.setAttribute("id", loginId);

                return true;
            }
        } else {
            throw new UserLoginException(ApiResponseCode.USER_AUTH_FAIL);
        }
    }

    public Optional<String> readCookie(HttpServletRequest request, String key) {
        System.out.println(request.getHeaders("Authorization"));
        return Arrays.stream(request.getCookies()).filter(c -> key.equals(c.getName())).map(Cookie::getValue).findAny();
    }
}
