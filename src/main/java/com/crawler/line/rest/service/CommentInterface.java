package com.crawler.line.rest.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommentInterface {
    @GET("/{gameName}/suggest/{articleId}/reply/list/Expage")
    public Call<ResponseBody> getArticleComments(@Path("gameName") String gameName, @Path("articleId") String articleId,
            @Query("page") Integer page);
}
