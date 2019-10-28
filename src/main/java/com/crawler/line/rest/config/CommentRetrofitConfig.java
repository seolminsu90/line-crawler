package com.crawler.line.rest.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.crawler.line.rest.service.CommentInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Configuration
public class CommentRetrofitConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Origin", "https://square.line.games")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
                        .build();
                return chain.proceed(request);
            }
        }).addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)).build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json().featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .modules(new JavaTimeModule()).build();
    }

    @Bean
    public Retrofit retrofit(ObjectMapper objectMapper, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl("https://square.line.games")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper)).client(okHttpClient).build();
    }

    @Bean
    public CommentInterface commentInterface(Retrofit retrofit) {
        return retrofit.create(CommentInterface.class);
    }

}
