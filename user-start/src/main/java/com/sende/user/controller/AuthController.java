package com.sende.user.controller;

import com.sende.user.api.AuthService;
import com.sende.user.api.UserService;
import com.sende.user.api.WorkspaceService;
import com.sende.user.api.dto.ForgotPasswordReqDTO;
import com.sende.user.api.dto.LoginReqDTO;
import com.sende.user.api.dto.LoginResDTO;
import com.sende.user.api.dto.ResetPasswordReqDTO;
import com.sende.user.api.dto.UserReqDTO;
import com.sende.user.api.dto.UserResDTO;
import com.sende.user.api.dto.WorkspaceReqDTO;
import com.sende.user.api.dto.WorkspaceResDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户微服务的 HTTP 网关层（最薄一层，仅做协议转换）
 * 所有业务逻辑都走 Dubbo RPC 调下游 service impl
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    @DubboReference
    private UserService userService;

    @DubboReference
    private AuthService authService;

    @DubboReference
    private WorkspaceService workspaceService;

    /** 注册（自动建默认 workspace） */
    @PostMapping("/auth/register")
    public Map<String, Object> register(@RequestBody RegisterReq req) {
        UserReqDTO dto = new UserReqDTO();
        dto.setUsername(req.getUsername());
        dto.setPassword(req.getPassword());
        dto.setEmail(req.getEmail());
        Long userId = userService.create(dto);

        // 自动建默认工作空间（避免下游 product/review 创建时 workspace_id 为 null）
        WorkspaceReqDTO ws = new WorkspaceReqDTO();
        ws.setWorkspaceName(req.getUsername() + "'s workspace");
        ws.setWorkspaceCode("WS_" + userId);
        ws.setOwnerUserId(userId);
        ws.setPlanType("FREE");
        ws.setStatus(1);
        workspaceService.create(ws);

        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("username", req.getUsername());
        return ok(data);
    }

    /** 登录拿 JWT */
    @PostMapping("/auth/login")
    public Map<String, Object> login(@RequestBody LoginReq req) {
        LoginReqDTO dto = new LoginReqDTO();
        dto.setAccount(req.getAccount() != null ? req.getAccount() : req.getUsername());
        dto.setPassword(req.getPassword());
        LoginResDTO res = authService.login(dto);

        Map<String, Object> data = new HashMap<>();
        data.put("token", res.getToken());
        data.put("userId", res.getUserId());
        data.put("username", res.getUsername());
        data.put("email", res.getEmail());
        data.put("defaultWorkspaceId", res.getDefaultWorkspaceId());
        data.put("expiresAt", res.getExpiresAt());
        return ok(data);
    }

    /** 当前用户信息（需 token） */
    @GetMapping("/users/me")
    public Map<String, Object> me(@RequestParam("token") String token) {
        Long userId = authService.validateAndGetUserId(token);
        if (userId == null) throw new IllegalArgumentException("invalid token");
        UserResDTO u = userService.getById(userId);
        return ok(u);
    }

    /** 我的工作空间列表 */
    @GetMapping("/workspaces")
    public Map<String, Object> listWorkspaces(@RequestParam("token") String token) {
        Long userId = authService.validateAndGetUserId(token);
        if (userId == null) throw new IllegalArgumentException("invalid token");
        List<WorkspaceResDTO> list = workspaceService.listByUserId(userId);
        return ok(list);
    }

    /** 发送密码重置验证码 */
    @PostMapping("/auth/forgot-code")
    public Map<String, Object> forgotCode(@RequestBody ForgotCodeReq req) {
        ForgotPasswordReqDTO dto = new ForgotPasswordReqDTO();
        dto.setEmail(req.getEmail());
        authService.sendResetCode(dto);
        // 安全起见：无论邮箱是否存在都返回 ok，避免被用来探测
        return ok("验证码已发送（如该邮箱已注册）");
    }

    /** 验证验证码 + 重置密码 */
    @PostMapping("/auth/reset-password")
    public Map<String, Object> resetPassword(@RequestBody ResetPasswordReq req) {
        ResetPasswordReqDTO dto = new ResetPasswordReqDTO();
        dto.setEmail(req.getEmail());
        dto.setCode(req.getCode());
        dto.setNewPassword(req.getNewPassword());
        authService.resetPassword(dto);
        return ok("密码已重置，请使用新密码登录");
    }

    private static Map<String, Object> ok(Object data) {
        Map<String, Object> r = new HashMap<>();
        r.put("code", 0);
        r.put("message", "ok");
        r.put("data", data);
        return r;
    }

    // ----- request bodies -----

    public static class RegisterReq {
        private String username;
        private String password;
        private String email;
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class LoginReq {
        private String account;
        private String username;
        private String password;
        public String getAccount() { return account; }
        public void setAccount(String account) { this.account = account; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class ForgotCodeReq {
        private String email;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class ResetPasswordReq {
        private String email;
        private String code;
        private String newPassword;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}