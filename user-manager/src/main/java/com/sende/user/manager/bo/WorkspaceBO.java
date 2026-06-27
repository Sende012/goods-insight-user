package com.sende.user.manager.bo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkspaceBO {
    /** 主键 ID */
    private Long id;
    /** 工作空间名 */
    private String workspaceName;
    /** 工作空间编码（短码，邀请用） */
    private String workspaceCode;
    /** 拥有者用户 ID */
    private Long ownerUserId;
    /** 套餐类型：FREE / PRO / ENTERPRISE */
    private String planType;
    /** 状态：1=启用，0=禁用 */
    private Integer status;
    /** 创建人 */
    private String createdBy;
    /** 更新人 */
    private String updatedBy;
    /** 创建时间 */
    private LocalDateTime createdTime;
    /** 更新时间 */
    private LocalDateTime updatedTime;
}