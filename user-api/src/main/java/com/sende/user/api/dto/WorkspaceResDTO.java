package com.sende.user.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class WorkspaceResDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String workspaceName;
    private String workspaceCode;
    private Long ownerUserId;
    private String planType;
    private Integer status;
    private LocalDateTime createdTime;
}