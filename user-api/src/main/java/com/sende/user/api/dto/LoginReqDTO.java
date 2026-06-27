package com.sende.user.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginReqDTO implements Serializable {
    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    /** username 或 email */
    private String account;
    /** 明文密码（传输 HTTPS，服务端 BCrypt 校验） */
    private String password;
}