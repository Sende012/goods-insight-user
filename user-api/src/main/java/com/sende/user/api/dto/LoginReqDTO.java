package com.sende.user.api.dto;

import lombok.Data;

@Data
public class LoginReqDTO {
    /** username 或 email */
    private String account;
    private String password;
}