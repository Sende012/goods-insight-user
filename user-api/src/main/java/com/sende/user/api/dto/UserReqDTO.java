package com.sende.user.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserReqDTO implements Serializable {
    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    /** 修改/删除时必填 */
    private Long id;
    /** 登录用户名 */
    private String username;
    /** 邮箱 */
    private String email;
    /** 仅创建时必填 */
    private String password;
    /** 头像 URL */
    private String avatar;
    /** 1 启用 / 0 禁用 */
    private Integer status;
}