package com.crawler.line.crawler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.crawler.line.api.game.repository.GameRepository;
import com.crawler.line.crawler.service.ArticleReadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@Configuration
public class ScheduleConfig {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    ArticleReadService articleReadService;

    // 1분에 1번 동작
    @Scheduled(fixedDelay = 60000)
    public void scheduling() {
        log.info("게시글을 가져옵니다.");
        gameRepository.findAll().parallelStream().forEach(game -> {
            articleReadService.readArticle(game);
        });
    }
}
