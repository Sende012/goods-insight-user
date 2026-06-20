package com.sende.user.service.impl;

import com.sende.user.api.UserService;
import com.sende.user.api.dto.UserReqDTO;
import com.sende.user.api.dto.UserResDTO;
import com.sende.user.manager.SysUserManager;
import com.sende.user.manager.bo.SysUserBO;
import com.sende.user.service.converter.UserDtoConverter;
import com.sende.user.service.util.PasswordUtil;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@DubboService
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserManager sysUserManager;

    @Resource
    private UserDtoConverter userDtoConverter;

    @Override
    public Long create(UserReqDTO req) {
        if (req.getPassword() == null || req.getPassword().isEmpty()) {
            throw new IllegalArgumentException("password 不能为空");
        }
        SysUserBO bo = userDtoConverter.toBO(req);
        bo.setPassword(PasswordUtil.encode(req.getPassword()));
        if (bo.getStatus() == null) bo.setStatus(1);
        return sysUserManager.create(bo);
    }

    @Override
    public void update(UserReqDTO req) {
        if (req.getId() == null) throw new IllegalArgumentException("id 不能为空");
        SysUserBO bo = new SysUserBO();
        bo.setId(req.getId());
        bo.setUsername(req.getUsername());
        bo.setEmail(req.getEmail());
        bo.setAvatar(req.getAvatar());
        bo.setStatus(req.getStatus());
        bo.setUpdatedBy("system");
        sysUserManager.update(bo);
    }

    @Override
    public UserResDTO getById(Long id) {
        SysUserBO bo = sysUserManager.getById(id);
        return bo == null ? null : userDtoConverter.toDTO(bo);
    }

    @Override
    public UserResDTO getByUsername(String username) {
        SysUserBO bo = sysUserManager.getByUsername(username);
        return bo == null ? null : userDtoConverter.toDTO(bo);
    }

    @Override
    public UserResDTO getByEmail(String email) {
        SysUserBO bo = sysUserManager.getByEmail(email);
        return bo == null ? null : userDtoConverter.toDTO(bo);
    }

    @Override
    public List<UserResDTO> listByWorkspaceId(Long workspaceId) {
        List<SysUserBO> bos = sysUserManager.listByWorkspaceId(workspaceId);
        if (bos.isEmpty()) return Collections.emptyList();
        return bos.stream().map(userDtoConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public void softDelete(Long id) {
        sysUserManager.softDelete(id, "system");
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("新密码至少 6 位");
        }
        SysUserBO bo = sysUserManager.getById(userId);
        if (bo == null) throw new IllegalArgumentException("用户不存在");
        if (!PasswordUtil.matches(oldPassword, bo.getPassword())) {
            throw new IllegalArgumentException("原密码错误");
        }
        SysUserBO upd = new SysUserBO();
        upd.setId(userId);
        upd.setPassword(PasswordUtil.encode(newPassword));
        upd.setUpdatedBy(String.valueOf(userId));
        sysUserManager.update(upd);
    }
}