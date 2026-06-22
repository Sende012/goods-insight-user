package com.sende.user.api;

import com.sende.user.api.dto.ForgotPasswordReqDTO;
import com.sende.user.api.dto.LoginReqDTO;
import com.sende.user.api.dto.LoginResDTO;
import com.sende.user.api.dto.ResetPasswordReqDTO;

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

    /**
     * 发送密码重置验证码到指定邮箱（6 位数字，10 分钟有效）
     */
    void sendResetCode(ForgotPasswordReqDTO req);

    /**
     * 验证邮箱 + 验证码 + 重置密码
     */
    void resetPassword(ResetPasswordReqDTO req);
}