package com.sende.user.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WorkspaceReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String workspaceName;
    private String workspaceCode;
    private Long ownerUserId;
    /** FREE / BASIC / PRO */
    private String planType;
    private Integer status;
}