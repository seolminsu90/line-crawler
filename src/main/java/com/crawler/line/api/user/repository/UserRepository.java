package com.crawler.line.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crawler.line.api.user.domain.User;

public interface UserRepository extends JpaRepository<User, String>{
    public User findByIdAndPwd(String id, String pwd);
}
