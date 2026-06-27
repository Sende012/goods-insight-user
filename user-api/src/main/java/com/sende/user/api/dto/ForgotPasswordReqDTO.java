package com.sende.user.api.dto;

import java.io.Serializable;

/**
 * 发送密码重置验证码请求
 */
public class ForgotPasswordReqDTO implements Serializable {
    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 注册邮箱（验证码发到此邮箱） */
    private String email;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
