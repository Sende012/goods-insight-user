package com.sende.user.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResDTO {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private Integer status;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createdTime;
    /** 不返回 password hash */
}