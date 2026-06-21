package com.sende.user.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具（HS256，对称密钥）
 *
 * 注意：使用 JJWT 0.11+ API，secret 直接当字节数组传入（不做 base64 解码），
 * 避免标准 Base64 字符集不识别 '-' / '_' 的问题。
 */
public final class JwtUtil {

    private final SecretKey key;
    private final long expireMillis;

    public JwtUtil(String secret, long expireSeconds) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException("JWT secret 字节长度至少 32 位");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
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
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long parseUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
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