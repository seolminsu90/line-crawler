package com.crawler.line.api.article.service;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
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
import com.crawler.line.api.game.repository.GameRepository;
import com.crawler.line.crawler.selenium.Selenium;
import com.crawler.line.crawler.service.ArticleReadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleReadService articleReadService;

    public Page<Article> getArticleList(ArticleDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, 10, new Sort(Direction.DESC, "seq"));

        return articleRepository.findAll(pageable);
    }

    public Article crawlingPassive() {
        log.info("게시글을 수동으로 가져옵니다.");
        gameRepository.findAll().parallelStream().forEach(game -> {
            articleReadService.readArticle(game);
        });
        return null;
    }

    public List<Article> crawlingUpdateForArticle(ArticleDTO dto) {
        log.info("게시글을 새로 업데이트 합니다.");
        List<Article> articleList = articleRepository.findByArticleIdInOrderBySeqDesc(dto.getArticleIds());
        if (!articleList.isEmpty()) {
            Selenium selenium = new Selenium();
            articleList.stream().forEach(article -> {
                try {
                    articleReadService.readArticleDetail(article, selenium, false);
                } catch (NoSuchElementException e) {
                    log.error(e.getMessage());
                }
            });
        }
        //업데이트 된 게시글 새로 가져오기
        return articleRepository.findByArticleIdInOrderBySeqDesc(dto.getArticleIds());
    }
}
