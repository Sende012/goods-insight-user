package com.sende.user.manager.bo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUserBO {
    /** 主键 ID */
    private Long id;
    /** 登录用户名 */
    private String username;
    /** 邮箱（找回密码用） */
    private String email;
    /** BCrypt 加密后的密码（manager 层不暴露给 controller） */
    private String password;
    /** 头像 URL */
    private String avatar;
    /** 用户状态：1=启用，0=禁用 */
    private Integer status;
    /** 最近登录时间 */
    private LocalDateTime lastLoginTime;
    /** 创建人 */
    private String createdBy;
    /** 更新人 */
    private String updatedBy;
    /** 创建时间 */
    private LocalDateTime createdTime;
    /** 更新时间 */
    private LocalDateTime updatedTime;
}