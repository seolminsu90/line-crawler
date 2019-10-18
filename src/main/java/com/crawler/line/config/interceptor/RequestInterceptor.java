
package com.crawler.line.config.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("URI [{}], \nmethod [{}] \nheaders [{}]\nparameters [{}]", request.getRequestURI(),
                request.getMethod(), getHeaders(request), getParameters(request));

        return true;
    }

    private final String getParameters(HttpServletRequest request) {
        StringBuilder buffer = new StringBuilder();
        Enumeration<?> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String name = (String) params.nextElement();
            buffer.append(name).append("=\"").append(request.getParameter(name)).append("\"");
            if (params.hasMoreElements())
                buffer.append(", ");
        }
        return buffer.toString();
    }

    private final String getHeaders(HttpServletRequest request) {
        StringBuilder buffer = new StringBuilder();
        Enumeration<?> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = (String) headers.nextElement();
            buffer.append(name).append("=\"").append(request.getHeader(name)).append("\"");
            if (headers.hasMoreElements())
                buffer.append(", ");
        }
        return buffer.toString();
    }
}
