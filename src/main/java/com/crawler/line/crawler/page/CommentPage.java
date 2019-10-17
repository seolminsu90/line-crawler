package com.crawler.line.crawler.page;

import org.openqa.selenium.support.PageFactory;

import com.crawler.line.crawler.selenium.Selenium;

public class CommentPage implements Page {
    private Selenium selenium;

    public CommentPage(Selenium selenium) {
        this.selenium = selenium;
        PageFactory.initElements(this.selenium.getDriver(), this);
    }

    @Override
    public void navigate(String url) {

    }

}
