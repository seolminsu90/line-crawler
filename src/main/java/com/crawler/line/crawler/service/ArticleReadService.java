package com.crawler.line.crawler.service;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crawler.line.api.article.domain.Article;
import com.crawler.line.api.article.repository.ArticleRepository;
import com.crawler.line.api.game.domain.Game;
import com.crawler.line.crawler.page.ArticleDetailPage;
import com.crawler.line.crawler.page.ArticleListPage;
import com.crawler.line.crawler.selenium.Selenium;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleReadService {
    @Autowired
    ArticleRepository articleRepository;

    public void readArticle(Game game) {
        Selenium selenium = new Selenium();
        ArticleListPage listPage = new ArticleListPage(selenium);
        listPage.navigate(game.getTargetUrl());
        List<Article> articleList = listPage.getArticleList(game);
        if (!articleList.isEmpty()) {
            articleList.stream().forEach(article -> {
                try {
                    readArticleDetail(article, selenium, true);
                } catch (NoSuchElementException e) {
                    log.error(e.getMessage());
                }
            });
        }
    }

    public void readArticleDetail(Article article, Selenium selenium, boolean existsCheck) {
        Article articleExists = articleRepository.findByArticleId(article.getArticleId());
        if (articleExists == null || !existsCheck) {
            ArticleDetailPage detailPage = new ArticleDetailPage(selenium);
            String detailUrl = "https://square.line.games/" + article.getBbsGroupId() + "/" + article.getBbsId()
                    + "/expage/" + article.getArticleId();
            detailPage.navigate(detailUrl);
            try {
                Article finalArticle = detailPage.getDetailInfo(article);
                articleRepository.save(finalArticle);
            } catch (NoSuchElementException e) {
                log.error(e.getMessage());
            }
        } else {
            log.info("이미 읽어들인 게시글입니다. ArticleId : {}", article.getArticleId());
        }
    }
}
