package com.crawler.line.api.article.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_ARTICLE")
@JsonInclude(Include.NON_NULL)
public class Article implements Serializable {
    @Id
    private String articleId;
    private Long seq;
    private String writer;
    private String title;
    private String content;
    private Date regDate;
    private String gameName;
    private String bbsGroupId;
    private String bbsId;
    private Long likeCnt;
    private Long unlikeCnt;
    private Long commentCnt;
    private Long viewCnt;
}
