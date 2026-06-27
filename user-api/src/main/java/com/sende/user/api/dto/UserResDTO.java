package com.sende.user.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserResDTO implements Serializable {
    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    /** 主键 ID */
    private Long id;
    /** 用户名 */
    private String username;
    /** 邮箱 */
    private String email;
    /** 头像 URL */
    private String avatar;
    /** 1=启用，0=禁用 */
    private Integer status;
    /** 最近登录时间 */
    private LocalDateTime lastLoginTime;
    /** 创建时间 */
    private LocalDateTime createdTime;
    /** 不返回 password hash */
}