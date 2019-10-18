package com.crawler.line.api.user.domain;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UserDTO implements Serializable {
    @Size(min = 8, max = 30)
    @Email
    private String id;

    @Size(min = 8, max = 30)
    private String pwd;

    @Size(min = 8, max = 30)
    private String pwdNew;

    @Size(min = 8, max = 30)
    private String pwdRe;
}
