package com.sende.user.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /** username 或 email */
    private String account;
    private String password;
}