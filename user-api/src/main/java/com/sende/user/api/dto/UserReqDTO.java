package com.sende.user.api.dto;

import lombok.Data;

@Data
public class UserReqDTO {
    /** 修改/删除时必填 */
    private Long id;
    private String username;
    private String email;
    /** 仅创建时必填 */
    private String password;
    private String avatar;
    /** 1 启用 / 0 禁用 */
    private Integer status;
}