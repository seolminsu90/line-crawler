package com.crawler.line.config.interceptor;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.crawler.line.api.user.domain.User;
import com.crawler.line.api.user.repository.UserRepository;
import com.crawler.line.config.domain.ApiResponseCode;
import com.crawler.line.config.exception.UserLoginException;
import com.crawler.line.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    UserRepository userRepository;

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

                Optional<User> user = userRepository.findById(loginId);

                if (!user.isPresent()) {
                    throw new UserLoginException(ApiResponseCode.USER_NOTFOUND);
                }

                log.info("Login ID : {}", loginId);
                request.setAttribute("id", loginId);
            } catch (Exception e) {
                throw new UserLoginException(ApiResponseCode.TOKEN_ERROR);
            }
            return true;
        }
    }
}
