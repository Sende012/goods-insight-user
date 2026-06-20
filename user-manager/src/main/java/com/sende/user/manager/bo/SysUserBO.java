package com.sende.user.manager.bo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUserBO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String avatar;
    private Integer status;
    private LocalDateTime lastLoginTime;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}