package com.sende.user.api.dto;

import java.io.Serializable;

/**
 * 验证并重置密码请求
 */
public class ResetPasswordReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String code;
    private String newPassword;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
