package com.sende.user.dao.dataobject;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkspaceDO {
    /** 主键 ID */
    private Long id;
    /** 工作空间名称 */
    private String workspaceName;
    /** 工作空间编码（唯一） */
    private String workspaceCode;
    /** 拥有者用户 ID */
    private Long ownerUserId;
    /** 套餐类型（FREE/PRO 等） */
    private String planType;
    /** 状态（1=启用，0=禁用） */
    private Integer status;
    /** 创建人 */
    private String createdBy;
    /** 更新人 */
    private String updatedBy;
    /** 创建时间 */
    private LocalDateTime createdTime;
    /** 更新时间 */
    private LocalDateTime updatedTime;
    /** 软删标记（0=未删，1=已删） */
    private Integer isDeleted;
}