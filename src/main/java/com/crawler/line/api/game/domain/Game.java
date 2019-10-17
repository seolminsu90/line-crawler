package com.crawler.line.api.game.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_GAME")
@JsonInclude(Include.NON_NULL)
public class Game {
    @Id
    private Long seq;
    private String gameName;
    private String targetUrl;
}
