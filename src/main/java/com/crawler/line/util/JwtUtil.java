package com.crawler.line.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.crawler.line.config.domain.ApiResponseCode;
import com.crawler.line.config.exception.UserLoginException;

public class JwtUtil {
    private final static String KEY = "LINEGAMES";

    public static String createToken(String id) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            return JWT.create().withIssuer("line")
                    .withExpiresAt(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000))).withClaim("id", id)
                    .sign(algorithm);
        } catch (Exception e) {
            throw new UserLoginException(ApiResponseCode.TOKEN_ERROR);
        }
    }

    public static String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("line").acceptExpiresAt(5 * 60).build();
            return verifier.verify(token).getClaim("id").asString();
        } catch (Exception e) {
            throw new UserLoginException(ApiResponseCode.TOKEN_ERROR);
        }
    }
}