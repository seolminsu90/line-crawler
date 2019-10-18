package com.crawler.line.api.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return new ApiResponse<>(articleService.getArticleList(dto), ApiResponseCode.OK);
    }

    @PostMapping("/crawl")
    public ApiResponse<Article> crawlingPassive() {
        return new ApiResponse<>(articleService.crawlingPassive(), ApiResponseCode.OK);
    }
    
    @PostMapping("/update")
    public ApiResponse<Article> crawlingUpdateForArticle(@RequestBody ArticleDTO dto) {
        return new ApiResponse<>(articleService.crawlingUpdateForArticle(dto), ApiResponseCode.OK);
    }
}
