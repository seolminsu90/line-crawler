package com.crawler.line.crawler.page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crawler.line.api.article.domain.Article;
import com.crawler.line.config.domain.ApiResponseCode;
import com.crawler.line.config.exception.ScrapeException;
import com.crawler.line.crawler.selenium.Selenium;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArticleDetailPage implements Page {
    private Selenium selenium;

    public ArticleDetailPage(Selenium selenium) {
        this.selenium = selenium;
        PageFactory.initElements(this.selenium.getDriver(), this);
    }

    @FindBy(className = "articleView")
    private WebElement articleViewWrap;

    @Override
    public void navigate(String url) {
        log.info(url);
        this.selenium.navigateTo(url);
    }

    public Article getDetailInfo(Article article) {
        try {
            Document doc = Jsoup.parse(articleViewWrap.getAttribute("outerHTML"));
            String title = doc.selectFirst(".subj strong").text();
            String subject = doc.selectFirst(".subj-data").text();
            String content = doc.selectFirst(".viewWrap").html();
            String likeCnt = doc.selectFirst(".like span").text();
            String unlikeCnt = doc.selectFirst(".dislike span").text();

            doc.selectFirst(".cmtnum a i").remove();
            String commentCnt = doc.selectFirst(".cmtnum a").text();

            String[] subjectSplit = subject.split(" 조회 ");
            String writer = subjectSplit[0];
            String viewCnt = subjectSplit[1].split(" ")[0];

            article.setTitle(title);
            article.setWriter(writer);
            article.setContent(content);
            article.setLikeCnt(Long.parseLong(likeCnt));
            article.setUnlikeCnt(Long.parseLong(unlikeCnt));
            article.setViewCnt(Long.parseLong(viewCnt));
            article.setCommentCnt(Long.parseLong(commentCnt));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ScrapeException(ApiResponseCode.SCRAPE_ERROR);
        }
        return article;
    }

}
