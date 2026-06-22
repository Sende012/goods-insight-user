package com.sende.user.manager;

import com.sende.user.dao.dataobject.SysUserDO;
import com.sende.user.dao.mapper.SysUserMapper;
import com.sende.user.manager.bo.SysUserBO;
import com.sende.user.manager.converter.SysUserDoConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户事务层
 */
@Component
public class SysUserManager {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserDoConverter sysUserDoConverter;

    @Transactional(rollbackFor = Exception.class)
    public Long create(SysUserBO bo) {
        SysUserDO do_ = sysUserDoConverter.toDO(bo);
        do_.setIsDeleted(0);
        sysUserMapper.insert(do_);
        return do_.getId();
    }

    public SysUserBO getById(Long id) {
        SysUserDO do_ = sysUserMapper.selectById(id);
        return do_ == null ? null : sysUserDoConverter.toBO(do_);
    }

    public SysUserBO getByUsername(String username) {
        SysUserDO do_ = sysUserMapper.selectByUsername(username);
        return do_ == null ? null : sysUserDoConverter.toBO(do_);
    }

    public SysUserBO getByEmail(String email) {
        SysUserDO do_ = sysUserMapper.selectByEmail(email);
        return do_ == null ? null : sysUserDoConverter.toBO(do_);
    }

    public List<SysUserBO> listByWorkspaceId(Long workspaceId) {
        List<SysUserDO> dos = sysUserMapper.selectByWorkspaceId(workspaceId);
        if (dos == null || dos.isEmpty()) return Collections.emptyList();
        return dos.stream().map(sysUserDoConverter::toBO).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public int update(SysUserBO bo) {
        SysUserDO do_ = sysUserDoConverter.toDO(bo);
        return sysUserMapper.updateById(do_);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updatePassword(Long userId, String encodedPassword) {
        SysUserBO upd = new SysUserBO();
        upd.setId(userId);
        upd.setPassword(encodedPassword);
        upd.setUpdatedBy(String.valueOf(userId));
        return update(upd);
    }

    @Transactional(rollbackFor = Exception.class)
    public int softDelete(Long id, String updatedBy) {
        return sysUserMapper.softDelete(id, updatedBy);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateLastLoginTime(Long id) {
        return sysUserMapper.updateLastLoginTime(id);
    }
}