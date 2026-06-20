package com.sende.user.manager;

import com.sende.user.dao.dataobject.WorkspaceDO;
import com.sende.user.dao.mapper.WorkspaceMapper;
import com.sende.user.manager.bo.WorkspaceBO;
import com.sende.user.manager.converter.WorkspaceDoConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工作空间事务层
 */
@Component
public class WorkspaceManager {

    @Resource
    private WorkspaceMapper workspaceMapper;

    @Resource
    private WorkspaceDoConverter workspaceDoConverter;

    @Transactional(rollbackFor = Exception.class)
    public Long create(WorkspaceBO bo) {
        WorkspaceDO do_ = workspaceDoConverter.toDO(bo);
        do_.setIsDeleted(0);
        if (do_.getStatus() == null) do_.setStatus(1);
        if (do_.getPlanType() == null) do_.setPlanType("FREE");
        workspaceMapper.insert(do_);
        return do_.getId();
    }

    public WorkspaceBO getById(Long id) {
        WorkspaceDO do_ = workspaceMapper.selectById(id);
        return do_ == null ? null : workspaceDoConverter.toBO(do_);
    }

    public WorkspaceBO getByCode(String code) {
        WorkspaceDO do_ = workspaceMapper.selectByCode(code);
        return do_ == null ? null : workspaceDoConverter.toBO(do_);
    }

    public List<WorkspaceBO> listByUserId(Long userId) {
        List<WorkspaceDO> dos = workspaceMapper.selectByUserId(userId);
        if (dos == null || dos.isEmpty()) return Collections.emptyList();
        return dos.stream().map(workspaceDoConverter::toBO).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public int update(WorkspaceBO bo) {
        WorkspaceDO do_ = workspaceDoConverter.toDO(bo);
        return workspaceMapper.updateById(do_);
    }

    @Transactional(rollbackFor = Exception.class)
    public int softDelete(Long id, String updatedBy) {
        return workspaceMapper.softDelete(id, updatedBy);
    }
}