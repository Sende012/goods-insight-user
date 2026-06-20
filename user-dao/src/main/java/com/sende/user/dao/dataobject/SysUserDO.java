package com.sende.user.dao.dataobject;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUserDO {
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
    private Integer isDeleted;
}