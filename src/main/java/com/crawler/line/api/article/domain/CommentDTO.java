package com.crawler.line.api.article.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CommentDTO {
    private String gameName;
    private String articleId;
    private Integer page;
}
