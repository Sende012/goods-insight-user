package com.sende.user.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LoginResDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    private Long userId;
    private String username;
    private String email;
    /** 用户的默认工作空间 ID（首个 OWNER 工作空间） */
    private Long defaultWorkspaceId;
    private LocalDateTime expiresAt;
}