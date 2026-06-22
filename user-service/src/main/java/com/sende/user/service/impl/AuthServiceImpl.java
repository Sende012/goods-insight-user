package com.sende.user.service.impl;

import com.sende.user.api.AuthService;
import com.sende.user.api.WorkspaceService;
import com.sende.user.api.dto.ForgotPasswordReqDTO;
import com.sende.user.api.dto.LoginReqDTO;
import com.sende.user.api.dto.LoginResDTO;
import com.sende.user.api.dto.ResetPasswordReqDTO;
import com.sende.user.api.dto.WorkspaceResDTO;
import com.sende.user.dao.dataobject.PasswordResetCodeDO;
import com.sende.user.manager.PasswordResetCodeManager;
import com.sende.user.manager.SysUserManager;
import com.sende.user.manager.bo.SysUserBO;
import com.sende.user.service.util.EmailService;
import com.sende.user.service.util.JwtUtil;
import com.sende.user.service.util.PasswordUtil;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * 登录认证服务（Dubbo RPC）
 *
 * 调用方：网关层（HTTP gateway）或其他服务。
 * 网关拿到 token 后，自行校验后透传到下游。
 */
@DubboService
public class AuthServiceImpl implements AuthService {

    private static final Random RANDOM = new Random();

    @Resource
    private SysUserManager sysUserManager;

    @Resource
    private PasswordResetCodeManager passwordResetCodeManager;

    @Resource
    private WorkspaceService workspaceService;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private EmailService emailService;

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

    @Override
    public void sendResetCode(ForgotPasswordReqDTO req) {
        if (req.getEmail() == null || req.getEmail().isEmpty()) {
            throw new IllegalArgumentException("email 不能为空");
        }
        SysUserBO user = sysUserManager.getByEmail(req.getEmail());
        if (user == null) {
            // 安全考虑：邮箱不存在也返回成功，避免被用来探测注册情况
            return;
        }
        String code = String.format("%06d", RANDOM.nextInt(1_000_000));
        passwordResetCodeManager.create(req.getEmail(), code);
        emailService.sendResetCode(req.getEmail(), code);
    }

    @Override
    public void resetPassword(ResetPasswordReqDTO req) {
        if (req.getEmail() == null || req.getCode() == null || req.getNewPassword() == null) {
            throw new IllegalArgumentException("email / code / newPassword 都不能为空");
        }
        if (req.getNewPassword().length() < 6) {
            throw new IllegalArgumentException("新密码至少 6 位");
        }
        PasswordResetCodeDO latest = passwordResetCodeManager.findLatestValid(req.getEmail());
        if (latest == null) {
            throw new IllegalArgumentException("验证码无效或已过期");
        }
        if (!latest.getCode().equals(req.getCode())) {
            throw new IllegalArgumentException("验证码错误");
        }
        SysUserBO user = sysUserManager.getByEmail(req.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        sysUserManager.updatePassword(user.getId(), PasswordUtil.encode(req.getNewPassword()));
        passwordResetCodeManager.markUsed(latest.getId());
    }
}