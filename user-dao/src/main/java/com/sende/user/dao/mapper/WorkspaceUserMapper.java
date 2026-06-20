package com.sende.user.dao.mapper;

import com.sende.user.dao.dataobject.WorkspaceUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkspaceUserMapper {

    Long insert(WorkspaceUserDO record);

    int deleteByWorkspaceAndUser(@Param("workspaceId") Long workspaceId,
                                 @Param("userId") Long userId);

    List<WorkspaceUserDO> selectByWorkspaceId(@Param("workspaceId") Long workspaceId);

    List<WorkspaceUserDO> selectByUserId(@Param("userId") Long userId);

    WorkspaceUserDO selectOne(@Param("workspaceId") Long workspaceId,
                              @Param("userId") Long userId);
}