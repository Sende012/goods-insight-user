package com.sende.user.dao.mapper;

import com.sende.user.dao.dataobject.SysUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {

    Long insert(SysUserDO userDO);

    SysUserDO selectById(@Param("id") Long id);

    SysUserDO selectByUsername(@Param("username") String username);

    SysUserDO selectByEmail(@Param("email") String email);

    List<SysUserDO> selectByWorkspaceId(@Param("workspaceId") Long workspaceId);

    int updateById(@Param("user") SysUserDO userDO);

    int softDelete(@Param("id") Long id, @Param("updatedBy") String updatedBy);

    int updateLastLoginTime(@Param("id") Long id);
}