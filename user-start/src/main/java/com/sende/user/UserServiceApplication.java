package com.sende.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sende.user.service.util.JwtUtil;

/**
 * 用户与工作空间微服务启动入口
 */
@SpringBootApplication(scanBasePackages = {"com.sende.user"})
@EnableDubbo
@MapperScan("com.sende.user.dao.mapper")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    /**
     * JWT 工具 Bean（密钥从配置读取，dev 兜底）
     */
    @Bean
    public JwtUtil jwtUtil(
            @org.springframework.beans.factory.annotation.Value("${app.jwt.secret:dev-secret-key-please-change-in-production-min-32-chars}") String secret,
            @org.springframework.beans.factory.annotation.Value("${app.jwt.expire-seconds:86400}") long expireSeconds) {
        return new JwtUtil(secret, expireSeconds);
    }
}