package com.sende.user.dao.dataobject;

import java.util.Date;

/**
 * 密码重置验证码 DO
 */
public class PasswordResetCodeDO {
    /** 主键 ID */
    private Long id;
    /** 接收验证码的邮箱 */
    private String email;
    /** 验证码 */
    private String code;
    /** 过期时间 */
    private Date expiresAt;
    /** 是否已使用（0=未使用，1=已使用） */
    private Integer used;
    /** 创建时间 */
    private Date createdTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Date getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Date expiresAt) { this.expiresAt = expiresAt; }
    public Integer getUsed() { return used; }
    public void setUsed(Integer used) { this.used = used; }
    public Date getCreatedTime() { return createdTime; }
    public void setCreatedTime(Date createdTime) { this.createdTime = createdTime; }
}
