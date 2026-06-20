package com.sende.user.service.impl;

import com.sende.user.api.AuthService;
import com.sende.user.api.WorkspaceService;
import com.sende.user.api.dto.LoginReqDTO;
import com.sende.user.api.dto.LoginResDTO;
import com.sende.user.api.dto.WorkspaceResDTO;
import com.sende.user.manager.SysUserManager;
import com.sende.user.manager.bo.SysUserBO;
import com.sende.user.service.util.JwtUtil;
import com.sende.user.service.util.PasswordUtil;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录认证服务（Dubbo RPC）
 *
 * 调用方：网关层（HTTP gateway）或其他服务。
 * 网关拿到 token 后，自行校验后透传到下游。
 */
@DubboService
public class AuthServiceImpl implements AuthService {

    @Resource
    private SysUserManager sysUserManager;

    @Resource
    private WorkspaceService workspaceService;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public LoginResDTO login(LoginReqDTO req) {
        if (req.getAccount() == null || req.getPassword() == null) {
            throw new IllegalArgumentException("account / password 不能为空");
        }
        SysUserBO user;
        if (req.getAccount().contains("@")) {
            user = sysUserManager.getByEmail(req.getAccount());
        } else {
            user = sysUserManager.getByUsername(req.getAccount());
        }
        if (user == null) {
            throw new IllegalArgumentException("账号或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new IllegalArgumentException("账号已停用");
        }
        if (!PasswordUtil.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("账号或密码错误");
        }

        // 记录登录时间
        sysUserManager.updateLastLoginTime(user.getId());

        // 取首个工作空间
        Long defaultWorkspaceId = null;
        List<WorkspaceResDTO> wss = workspaceService.listByUserId(user.getId());
        if (wss != null && !wss.isEmpty()) {
            defaultWorkspaceId = wss.get(0).getId();
        }

        LoginResDTO res = new LoginResDTO();
        res.setToken(jwtUtil.issue(user.getId(), user.getUsername()));
        res.setUserId(user.getId());
        res.setUsername(user.getUsername());
        res.setEmail(user.getEmail());
        res.setDefaultWorkspaceId(defaultWorkspaceId);
        res.setExpiresAt(jwtUtil.expireAt());
        return res;
    }

    @Override
    public Long validateAndGetUserId(String token) {
        return jwtUtil.parseUserId(token);
    }
}