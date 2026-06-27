package com.sende.user.dao.dataobject;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUserDO {
    /** 主键 ID */
    private Long id;
    /** 用户名 */
    private String username;
    /** 邮箱 */
    private String email;
    /** BCrypt 哈希 */
    private String password;
    /** 头像 URL */
    private String avatar;
    /** 状态（1=启用，0=禁用） */
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
    /** 软删标记（0=未删，1=已删） */
    private Integer isDeleted;
}