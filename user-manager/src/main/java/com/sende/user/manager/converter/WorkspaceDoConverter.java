package com.sende.user.manager.converter;

import com.sende.user.dao.dataobject.WorkspaceDO;
import com.sende.user.manager.bo.WorkspaceBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkspaceDoConverter {

    WorkspaceBO toBO(WorkspaceDO workspaceDO);

    WorkspaceDO toDO(WorkspaceBO workspaceBO);
}