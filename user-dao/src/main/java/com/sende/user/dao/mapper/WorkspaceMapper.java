package com.sende.user.dao.mapper;

import com.sende.user.dao.dataobject.WorkspaceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkspaceMapper {

    Long insert(WorkspaceDO workspaceDO);

    WorkspaceDO selectById(@Param("id") Long id);

    WorkspaceDO selectByCode(@Param("workspaceCode") String workspaceCode);

    List<WorkspaceDO> selectByUserId(@Param("userId") Long userId);

    int updateById(@Param("workspace") WorkspaceDO workspaceDO);

    int softDelete(@Param("id") Long id, @Param("updatedBy") String updatedBy);
}