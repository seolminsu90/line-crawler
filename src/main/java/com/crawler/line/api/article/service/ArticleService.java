package com.crawler.line.api.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.crawler.line.api.article.domain.Article;
import com.crawler.line.api.article.domain.ArticleDTO;
import com.crawler.line.api.article.repository.ArticleRepository;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public Page<Article> getArticleList(ArticleDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), 10, new Sort(Direction.DESC, "regDate"));

        return articleRepository.findAll(pageable);
    }
}
