package com.crawler.line.api.user.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crawler.line.api.user.domain.User;
import com.crawler.line.api.user.domain.UserDTO;
import com.crawler.line.api.user.service.UserService;
import com.crawler.line.config.domain.ApiResponse;
import com.crawler.line.config.domain.ApiResponseCode;

//유저
@RequestMapping("/api/users")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    // 유저 로그인
    @PostMapping("/login")
    public ApiResponse<User> login(@RequestBody @Valid UserDTO dto, HttpServletResponse response) {
        return new ApiResponse<>(userService.login(dto, response), ApiResponseCode.OK);
    }

    // 유저 회원 가입
    @PostMapping("/signin")
    public ApiResponse<User> signin(@RequestBody @Valid UserDTO dto) {
        return new ApiResponse<>(userService.signin(dto), ApiResponseCode.OK);
    }

    // 유저 임시비밀번호 발송 (Gmail 계정 정보를 입력해야 사용 가능)
    @PostMapping("/send-temp-pwd")
    public ApiResponse<User> sendTempPassword(@RequestBody @Valid UserDTO dto) {
        return new ApiResponse<>(userService.sendTempPassword(dto.getId()), ApiResponseCode.OK);
    }

    // 유저 정보 수정
    @PutMapping
    public ApiResponse<User> update(@RequestBody @Valid UserDTO dto, @RequestAttribute String id) {
        return new ApiResponse<>(userService.update(dto, id), ApiResponseCode.OK);
    }

    // 유저 삭제
    @DeleteMapping
    public ApiResponse<User> delete(@RequestAttribute String id, HttpServletResponse response) {
        return new ApiResponse<>(userService.delete(id, response), ApiResponseCode.OK);
    }

}
