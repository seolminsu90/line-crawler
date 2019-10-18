package com.crawler.line.api.article.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ArticleDTO {
    private Integer page;
    private List<String> articleIds;

    public Integer getPage() {
        if (this.page == null) {
            return 1;
        } else {
            return this.page;
        }
    }
}
