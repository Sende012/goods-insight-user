package com.sende.user.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LoginResDTO implements Serializable {
    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    /** JWT Token（Authorization: Bearer <token>） */
    private String token;
    /** 用户 ID */
    private Long userId;
    /** 用户名 */
    private String username;
    /** 邮箱 */
    private String email;
    /** 用户的默认工作空间 ID（首个 OWNER 工作空间） */
    private Long defaultWorkspaceId;
    /** Token 过期时间 */
    private LocalDateTime expiresAt;
}