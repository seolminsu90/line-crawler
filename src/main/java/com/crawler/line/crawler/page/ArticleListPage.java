package com.crawler.line.crawler.page;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crawler.line.api.article.domain.Article;
import com.crawler.line.api.game.domain.Game;
import com.crawler.line.config.domain.ApiResponseCode;
import com.crawler.line.config.exception.ScrapeException;
import com.crawler.line.crawler.selenium.Selenium;
import com.crawler.line.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArticleListPage implements Page {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    private Selenium selenium;

    public ArticleListPage(Selenium selenium) {
        this.selenium = selenium;
        PageFactory.initElements(this.selenium.getDriver(), this);
    }

    @FindBy(id = "boardList")
    private WebElement articleMainWrap;

    @Override
    public void navigate(String url) {
        this.selenium.navigateTo(url);
    }

    public List<Article> getArticleList(Game game) {
        List<Article> list = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(articleMainWrap.getAttribute("outerHTML"));

            // 공지 등록 게시글 제외
            Elements articleList = doc.select("li:not([noticeList])");

            if (articleList != null && !articleList.isEmpty()) {
                articleList.forEach(articleListOne -> {
                    try {
                        Article article = new Article();
                        article.setGameName(game.getGameName());

                        Element linkElement = articleListOne.selectFirst(".subj strong a");

                        // 운영자 게시글 제외
                        if (linkElement.selectFirst(".nf-text-icon") != null) {
                            return;
                        }

                        // 링크에서 게임, 게시판, 아티클아이디 추출
                        String[] splitStr = linkElement.attr("href").replace("javascript:viewShowAction(", "")
                                .replace(");", "").replaceAll("'", "").split(",");
                        String regDate = articleListOne.selectFirst(".etc .date").text();

                        article.setBbsGroupId(splitStr[0]);
                        article.setBbsId(splitStr[1]);
                        article.setArticleId(Long.parseLong(splitStr[2]));

                        // 1일 이내의 글은 오늘 시간,분 을 제하여 변경 ..초도 있다
                        if (regDate.indexOf("시간 전") != -1 || regDate.indexOf("분 전") != -1
                                || regDate.indexOf("초 전") != -1) {
                            int interval = CommonUtil.getPureNumber(regDate);
                            Calendar calendar = Calendar.getInstance();
                            if (regDate.indexOf("분 전") != -1) {
                                calendar.add(Calendar.MINUTE, -interval);
                            } else if (regDate.indexOf("시간 전") != -1) {
                                calendar.add(Calendar.HOUR_OF_DAY, -interval);
                            }
                            article.setRegDate(calendar.getTime());
                        } else {
                            article.setRegDate(sdf.parse(regDate));
                        }

                        list.add(article);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.info("형식에 맞지 않는 게시물 : {}", articleListOne.html());
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ScrapeException(ApiResponseCode.SCRAPE_ERROR);
        }
        return list;
    }
}
