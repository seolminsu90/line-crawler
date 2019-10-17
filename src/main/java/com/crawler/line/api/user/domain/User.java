package com.crawler.line.api.user.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_USER")
@JsonInclude(Include.NON_NULL)
public class User implements Serializable {
    @Id
    private String id;

    @JsonIgnore
    private String pwd;

    @Transient
    private String token;
}
