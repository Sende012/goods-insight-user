package com.sende.user.api;

import com.sende.user.api.dto.LoginReqDTO;
import com.sende.user.api.dto.LoginResDTO;

/**
 * 认证服务（Dubbo RPC）
 */
public interface AuthService {

    /**
     * 登录，返回 JWT token + 用户信息
     */
    LoginResDTO login(LoginReqDTO req);

    /**
     * 解析并校验 token，返回 userId（失败抛异常）
     */
    Long validateAndGetUserId(String token);
}