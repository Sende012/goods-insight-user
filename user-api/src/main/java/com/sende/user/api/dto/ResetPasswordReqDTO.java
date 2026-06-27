package com.sende.user.api.dto;

import java.io.Serializable;

/**
 * 验证并重置密码请求
 */
public class ResetPasswordReqDTO implements Serializable {
    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 注册邮箱 */
    private String email;
    /** 6 位数字验证码（邮件发送） */
    private String code;
    /** 新密码（明文，服务端 BCrypt 加密） */
    private String newPassword;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
