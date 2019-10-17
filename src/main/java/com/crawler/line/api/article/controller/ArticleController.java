package com.crawler.line.api.article.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crawler.line.api.article.domain.Article;
import com.crawler.line.api.article.domain.ArticleDTO;
import com.crawler.line.config.domain.ApiResponse;

@RequestMapping("/api/articles")
@RestController
public class ArticleController {
    @GetMapping
    public ApiResponse<Article> getArticleList(@ModelAttribute ArticleDTO dto) {
        return null;
    }
}
