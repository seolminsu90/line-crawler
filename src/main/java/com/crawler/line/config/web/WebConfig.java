package com.crawler.line.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.crawler.line.config.interceptor.LoginInterceptor;
import com.crawler.line.config.interceptor.RequestInterceptor;

@EnableAsync
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**").excludePathPatterns("/",
                "/api/users/send-temp-pwd", "/api/users/login", "/api/users/signin");
        registry.addInterceptor(requestInterceptor).addPathPatterns("/*");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
