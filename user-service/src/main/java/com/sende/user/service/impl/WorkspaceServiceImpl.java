package com.sende.user.service.impl;

import com.sende.user.api.WorkspaceService;
import com.sende.user.api.dto.WorkspaceReqDTO;
import com.sende.user.api.dto.WorkspaceResDTO;
import com.sende.user.manager.WorkspaceManager;
import com.sende.user.manager.WorkspaceUserManager;
import com.sende.user.manager.bo.WorkspaceBO;
import com.sende.user.service.converter.WorkspaceDtoConverter;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@DubboService
public class WorkspaceServiceImpl implements WorkspaceService {

    @Resource
    private WorkspaceManager workspaceManager;

    @Resource
    private WorkspaceUserManager workspaceUserManager;

    @Resource
    private WorkspaceDtoConverter workspaceDtoConverter;

    @Override
    public Long create(WorkspaceReqDTO req) {
        if (req.getOwnerUserId() == null) {
            throw new IllegalArgumentException("ownerUserId 不能为空");
        }
        if (req.getWorkspaceCode() == null || req.getWorkspaceCode().isEmpty()) {
            throw new IllegalArgumentException("workspaceCode 不能为空");
        }
        WorkspaceBO bo = workspaceDtoConverter.toBO(req);
        bo.setCreatedBy(String.valueOf(req.getOwnerUserId()));
        bo.setUpdatedBy(String.valueOf(req.getOwnerUserId()));
        Long id = workspaceManager.create(bo);
        // 自动把 owner 加为 OWNER 成员
        workspaceUserManager.addMember(id, req.getOwnerUserId(), "OWNER");
        return id;
    }

    @Override
    public void update(WorkspaceReqDTO req) {
        if (req.getId() == null) throw new IllegalArgumentException("id 不能为空");
        WorkspaceBO bo = new WorkspaceBO();
        bo.setId(req.getId());
        bo.setWorkspaceName(req.getWorkspaceName());
        bo.setWorkspaceCode(req.getWorkspaceCode());
        bo.setOwnerUserId(req.getOwnerUserId());
        bo.setPlanType(req.getPlanType());
        bo.setStatus(req.getStatus());
        workspaceManager.update(bo);
    }

    @Override
    public WorkspaceResDTO getById(Long id) {
        WorkspaceBO bo = workspaceManager.getById(id);
        return bo == null ? null : workspaceDtoConverter.toDTO(bo);
    }

    @Override
    public WorkspaceResDTO getByCode(String workspaceCode) {
        WorkspaceBO bo = workspaceManager.getByCode(workspaceCode);
        return bo == null ? null : workspaceDtoConverter.toDTO(bo);
    }

    @Override
    public List<WorkspaceResDTO> listByUserId(Long userId) {
        List<WorkspaceBO> bos = workspaceManager.listByUserId(userId);
        if (bos.isEmpty()) return Collections.emptyList();
        return bos.stream().map(workspaceDtoConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public void softDelete(Long id) {
        workspaceManager.softDelete(id, "system");
    }

    @Override
    public void addMember(Long workspaceId, Long userId, String roleCode) {
        workspaceUserManager.addMember(workspaceId, userId, roleCode);
    }

    @Override
    public void removeMember(Long workspaceId, Long userId) {
        workspaceUserManager.removeMember(workspaceId, userId);
    }

    @Override
    public List<Long> listMemberUserIds(Long workspaceId) {
        return workspaceUserManager.listByWorkspaceId(workspaceId).stream()
                .map(b -> b.getUserId())
                .collect(Collectors.toList());
    }
}