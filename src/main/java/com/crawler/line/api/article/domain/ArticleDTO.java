package com.crawler.line.api.article.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ArticleDTO implements Serializable {
    private Integer page;

    public Integer getPage() {
        if (this.page == null) {
            return 1;
        } else {
            return this.page;
        }
    }
}
