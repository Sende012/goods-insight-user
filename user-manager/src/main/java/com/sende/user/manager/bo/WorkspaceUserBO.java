package com.sende.user.manager.bo;

import lombok.Data;

@Data
public class WorkspaceUserBO {
    /** 主键 ID */
    private Long id;
    /** 工作空间 ID */
    private Long workspaceId;
    /** 成员用户 ID */
    private Long userId;
    /** 角色编码：OWNER / ADMIN / MEMBER / VIEWER */
    private String roleCode;
}