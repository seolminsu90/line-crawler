package com.crawler.line.api.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crawler.line.api.article.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{
    public Article findByArticleId(Long articleId);
}
