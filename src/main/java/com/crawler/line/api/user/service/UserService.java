package com.crawler.line.api.user.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.crawler.line.api.user.domain.User;
import com.crawler.line.api.user.domain.UserDTO;
import com.crawler.line.api.user.repository.UserRepository;
import com.crawler.line.config.domain.ApiResponseCode;
import com.crawler.line.config.exception.UserLoginException;
import com.crawler.line.util.JwtUtil;
import com.crawler.line.util.SHAEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    // mail util에 옴겨옴겨~
    @Value("${mail.sender.id}")
    String gmailSenderId;

    @Value("${mail.sender.pwd}")
    String gmailSenderPwd;

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

    public User update(UserDTO dto) {
        Optional<User> existsCheck = userRepository.findById(dto.getId());

        if (existsCheck.isPresent()) {
            User user = existsCheck.get();
            try {
                // 패스워드 변경 시
                if (dto.getPwd().equals(dto.getPwdRe())) {
                    user.setPwd(SHAEncoder.encode(dto.getPwd()));
                }

                // 기타 추가 업데이트 사항추가...

                user.setId(dto.getId());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            userRepository.save(user);
        } else {
            throw new UserLoginException(ApiResponseCode.USER_NOTFOUND);
        }

        return null;
    }

    public User delete(UserDTO dto) {
        // 탈퇴 시 비밀번호 요구
        if (dto.getId() == null || dto.getPwd() == null) {
            throw new UserLoginException(ApiResponseCode.USER_LOGIN_ERROR);
        }

        Optional<User> existsCheck = userRepository.findById(dto.getId());

        if (existsCheck.isPresent()) {
            User user = existsCheck.get();

            try {
                if (dto.getPwd() == null) {
                    throw new UserLoginException(ApiResponseCode.USER_PWD_NOTMATCH);
                }

                user.setId(dto.getId());
                user.setPwd(SHAEncoder.encode(dto.getPwd()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            userRepository.delete(user);
        } else {
            throw new UserLoginException(ApiResponseCode.USER_NOTFOUND);
        }

        return null;
    }

    public User sendTempPassword(UserDTO dto) {
        return null;
    }
}
