package com.sende.user.dao.dataobject;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkspaceDO {
    private Long id;
    private String workspaceName;
    private String workspaceCode;
    private Long ownerUserId;
    private String planType;
    private Integer status;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Integer isDeleted;
}