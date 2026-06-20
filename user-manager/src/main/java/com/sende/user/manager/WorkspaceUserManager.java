package com.sende.user.manager;

import com.sende.user.dao.dataobject.WorkspaceUserDO;
import com.sende.user.dao.mapper.WorkspaceUserMapper;
import com.sende.user.manager.bo.WorkspaceUserBO;
import com.sende.user.manager.converter.WorkspaceUserDoConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工作空间成员事务层
 */
@Component
public class WorkspaceUserManager {

    @Resource
    private WorkspaceUserMapper workspaceUserMapper;

    @Resource
    private WorkspaceUserDoConverter workspaceUserDoConverter;

    @Transactional(rollbackFor = Exception.class)
    public Long addMember(Long workspaceId, Long userId, String roleCode) {
        WorkspaceUserDO existing = workspaceUserMapper.selectOne(workspaceId, userId);
        if (existing != null) {
            return existing.getId();
        }
        WorkspaceUserDO do_ = new WorkspaceUserDO();
        do_.setWorkspaceId(workspaceId);
        do_.setUserId(userId);
        do_.setRoleCode(roleCode == null ? "MEMBER" : roleCode);
        workspaceUserMapper.insert(do_);
        return do_.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public int removeMember(Long workspaceId, Long userId) {
        return workspaceUserMapper.deleteByWorkspaceAndUser(workspaceId, userId);
    }

    public List<WorkspaceUserBO> listByWorkspaceId(Long workspaceId) {
        List<WorkspaceUserDO> dos = workspaceUserMapper.selectByWorkspaceId(workspaceId);
        if (dos == null || dos.isEmpty()) return Collections.emptyList();
        return dos.stream().map(workspaceUserDoConverter::toBO).collect(Collectors.toList());
    }

    public List<WorkspaceUserBO> listByUserId(Long userId) {
        List<WorkspaceUserDO> dos = workspaceUserMapper.selectByUserId(userId);
        if (dos == null || dos.isEmpty()) return Collections.emptyList();
        return dos.stream().map(workspaceUserDoConverter::toBO).collect(Collectors.toList());
    }
}