package com.crawler.line.api.article.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.crawler.line.api.article.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, String> {
    public Article findByArticleId(String articleId);

    public Page<Article> findAll(Pageable request);

    public List<Article> findByArticleIdInOrderBySeqDesc(List<String> articleIds);
}
