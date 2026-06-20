package com.sende.user.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkspaceResDTO {
    private Long id;
    private String workspaceName;
    private String workspaceCode;
    private Long ownerUserId;
    private String planType;
    private Integer status;
    private LocalDateTime createdTime;
}