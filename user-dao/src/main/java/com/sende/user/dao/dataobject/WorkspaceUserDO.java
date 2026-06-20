package com.sende.user.dao.dataobject;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkspaceUserDO {
    private Long id;
    private Long workspaceId;
    private Long userId;
    /** OWNER / ADMIN / MEMBER */
    private String roleCode;
    private LocalDateTime createdTime;
}