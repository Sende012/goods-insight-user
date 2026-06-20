package com.sende.user.api;

import com.sende.user.api.dto.WorkspaceReqDTO;
import com.sende.user.api.dto.WorkspaceResDTO;

import java.util.List;

/**
 * 工作空间服务（Dubbo RPC）
 */
public interface WorkspaceService {

    /** 创建工作空间，并自动把 ownerUserId 加为 OWNER 成员 */
    Long create(WorkspaceReqDTO req);

    void update(WorkspaceReqDTO req);

    WorkspaceResDTO getById(Long id);

    WorkspaceResDTO getByCode(String workspaceCode);

    List<WorkspaceResDTO> listByUserId(Long userId);

    void softDelete(Long id);

    // ----- 成员管理 -----

    /** 添加成员 */
    void addMember(Long workspaceId, Long userId, String roleCode);

    /** 移除成员 */
    void removeMember(Long workspaceId, Long userId);

    /** 列出工作空间成员 userId 列表 */
    List<Long> listMemberUserIds(Long workspaceId);
}