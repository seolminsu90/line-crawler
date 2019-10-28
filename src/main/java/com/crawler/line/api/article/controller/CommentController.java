package com.crawler.line.api.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crawler.line.api.article.domain.Comment;
import com.crawler.line.api.article.service.CommentService;
import com.crawler.line.config.domain.ApiResponse;
import com.crawler.line.config.domain.ApiResponseCode;

@RequestMapping("/api/comments")
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping
    public ApiResponse<Comment> getCommentList(@RequestParam(required = true) String gameName,
            @RequestParam(required = true) String articleId, @RequestParam(required = true) Integer page) {
        return new ApiResponse<>(commentService.getCommentList(gameName, articleId, page), ApiResponseCode.OK);
    }
}
