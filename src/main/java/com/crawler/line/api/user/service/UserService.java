package com.crawler.line.api.user.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crawler.line.api.user.domain.User;
import com.crawler.line.api.user.domain.UserDTO;
import com.crawler.line.api.user.repository.UserRepository;
import com.crawler.line.config.domain.ApiResponseCode;
import com.crawler.line.config.exception.UserLoginException;
import com.crawler.line.util.JwtUtil;
import com.crawler.line.util.MailSender;
import com.crawler.line.util.SHAEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MailSender mailSender;

    public User login(UserDTO dto, HttpServletResponse httpResponse) {
        User user = null;

        if (dto.getId() == null || dto.getPwd() == null) {
            throw new UserLoginException(ApiResponseCode.USER_LOGIN_ERROR);
        }

        try {
            user = userRepository.findByIdAndPwd(dto.getId(), SHAEncoder.encode(dto.getPwd()));

            if (user != null) {
                String token = JwtUtil.createToken(dto.getId());
                Cookie setCookie = new Cookie("crawler-access-key", URLEncoder.encode(token, "UTF-8"));
                setCookie.setMaxAge(60 * 60 * 24 * 30);
                setCookie.setPath("/");
                httpResponse.addCookie(setCookie);
                user.setToken(token);

            } else {
                throw new UserLoginException(ApiResponseCode.USER_AUTH_FAIL);
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User signin(UserDTO dto) {
        User user = new User();

        if (dto.getId() == null || dto.getPwd() == null) {
            throw new UserLoginException(ApiResponseCode.USER_LOGIN_ERROR);
        }

        Optional<User> existsCheck = userRepository.findById(dto.getId());
        if (existsCheck.isPresent()) {
            throw new UserLoginException(ApiResponseCode.USER_EXISTS);
        } else {
            try {
                user.setId(dto.getId());
                user.setPwd(SHAEncoder.encode(dto.getPwd()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            userRepository.save(user);
        }
        return null;
    }

    public User update(UserDTO dto, String id) {
        try {
            User user = userRepository.findByIdAndPwd(id, SHAEncoder.encode(dto.getPwd()));
            if (user != null) {
                user.setPwd(SHAEncoder.encode(dto.getPwdNew()));

                // 기타 추가 업데이트 사항 필요시 추가...

                userRepository.save(user);
            } else {
                throw new UserLoginException(ApiResponseCode.USER_PWD_NOTMATCH);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User delete(String id, HttpServletResponse httpResponse) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());

            Cookie setCookie = new Cookie("crawler-access-key", "");
            setCookie.setMaxAge(0);
            setCookie.setPath("/");
            httpResponse.addCookie(setCookie);
        } else {
            throw new UserLoginException(ApiResponseCode.USER_PWD_NOTMATCH);
        }

        return null;
    }

    public User sendTempPassword(String id) {
        try {
            Optional<User> exists = userRepository.findById(id);
            if (exists.isPresent()) {
                User user = exists.get();
                String tempPassword = UUID.randomUUID().toString().replace(" ", "").substring(0, 8);
                log.info("Temp Password :: {}", tempPassword);
                mailSender.send(id, tempPassword);

                user.setPwd(SHAEncoder.encode(tempPassword));
                userRepository.save(user);
            } else {
                throw new UserLoginException(ApiResponseCode.USER_NOTFOUND);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
