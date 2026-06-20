package com.sende.user.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具（HS256，对称密钥）
 */
public final class JwtUtil {

    private final String secret;
    private final long expireMillis;

    public JwtUtil(String secret, long expireSeconds) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret 长度至少 32 位");
        }
        this.secret = secret;
        this.expireMillis = expireSeconds * 1000L;
    }

    public String issue(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", userId);
        claims.put("uname", username);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireMillis))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Long parseUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        Object uid = claims.get("uid");
        if (uid == null) throw new IllegalArgumentException("token 缺少 uid");
        return ((Number) uid).longValue();
    }

    public LocalDateTime expireAt() {
        return LocalDateTime.ofInstant(
                new Date(System.currentTimeMillis() + expireMillis).toInstant(),
                ZoneId.systemDefault());
    }
}