package com.sende.user.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class WorkspaceResDTO implements Serializable {
    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    /** 主键 ID */
    private Long id;
    /** 工作空间名 */
    private String workspaceName;
    /** 工作空间短码 */
    private String workspaceCode;
    /** 拥有者用户 ID */
    private Long ownerUserId;
    /** 套餐类型 FREE/PRO/ENTERPRISE */
    private String planType;
    /** 1=启用，0=禁用 */
    private Integer status;
    /** 创建时间 */
}