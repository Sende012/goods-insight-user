package com.sende.user.dao.dataobject;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkspaceUserDO {
    /** 主键 ID */
    private Long id;
    /** 工作空间 ID */
    private Long workspaceId;
    /** 用户 ID */
    private Long userId;
    /** OWNER / ADMIN / MEMBER */
    private String roleCode;
    /** 创建时间 */
    private LocalDateTime createdTime;
}