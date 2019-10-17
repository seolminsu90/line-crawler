package com.crawler.line.api.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crawler.line.api.article.domain.Article;
import com.crawler.line.api.article.domain.ArticleDTO;
import com.crawler.line.api.article.service.ArticleService;
import com.crawler.line.config.domain.ApiResponse;
import com.crawler.line.config.domain.ApiResponseCode;

@RequestMapping("/api/articles")
@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping
    public ApiResponse<Article> getArticleList(@ModelAttribute ArticleDTO dto) {
        Page<Article> list = articleService.getArticleList(dto);

        return new ApiResponse<>(list.getContent(), list.getPageable(), ApiResponseCode.OK);
    }
}
