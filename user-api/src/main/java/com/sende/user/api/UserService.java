package com.sende.user.api;

import com.sende.user.api.dto.UserReqDTO;
import com.sende.user.api.dto.UserResDTO;

import java.util.List;

/**
 * 用户服务（Dubbo RPC）
 */
public interface UserService {

    /** 创建用户，返回用户 ID */
    Long create(UserReqDTO req);

    /** 更新用户 */
    void update(UserReqDTO req);

    /** 按 ID 查询 */
    UserResDTO getById(Long id);

    /** 按用户名查询 */
    UserResDTO getByUsername(String username);

    /** 按邮箱查询 */
    UserResDTO getByEmail(String email);

    /** 按工作空间列出用户 */
    List<UserResDTO> listByWorkspaceId(Long workspaceId);

    /** 软删除 */
    void softDelete(Long id);

    /** 修改密码（仅校验旧密码后设置新密码） */
    void changePassword(Long userId, String oldPassword, String newPassword);
}