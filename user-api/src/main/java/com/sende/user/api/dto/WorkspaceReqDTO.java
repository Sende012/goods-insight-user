package com.sende.user.api.dto;

import lombok.Data;

@Data
public class WorkspaceReqDTO {
    private Long id;
    private String workspaceName;
    private String workspaceCode;
    private Long ownerUserId;
    /** FREE / BASIC / PRO */
    private String planType;
    private Integer status;
}