package com.crawler.line.api.article.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crawler.line.api.article.domain.Comment;
import com.crawler.line.rest.service.CommentInterface;

import okhttp3.ResponseBody;
import retrofit2.Response;

@Service
public class CommentService {
    @Autowired
    CommentInterface CommentInterface;

    public List<Comment> getCommentList(String gameName, String articleId, Integer page) {
        List<Comment> commentList = new ArrayList<>();
        try {
            Response<ResponseBody> body = CommentInterface.getArticleComments(gameName, articleId, page).execute();
            ResponseBody resBody = body.body();
            Document doc = Jsoup.parse(resBody.string());
            Elements comments = doc.select("li");

            comments.stream().forEach(comment -> {
                Element writerId = comment.select(".userSubj strong").first();
                Element content = comment.select(".userCmt label").first();
                if (writerId != null && content != null) {
                    commentList.add(new Comment(writerId.text(), content.text()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return commentList;
    }
}
