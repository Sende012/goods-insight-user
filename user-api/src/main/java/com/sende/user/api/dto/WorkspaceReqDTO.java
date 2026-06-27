package com.sende.user.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WorkspaceReqDTO implements Serializable {
    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;
    /** 主键 ID（修改/删除时必填） */
    private Long id;
    /** 工作空间名 */
    private String workspaceName;
    /** 工作空间短码（邀请用） */
    private String workspaceCode;
    /** 拥有者用户 ID */
    private Long ownerUserId;
    /** FREE / BASIC / PRO */
    private String planType;
    /** 1=启用，0=禁用 */
    private Integer status;
}