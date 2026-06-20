package com.sende.user.manager.converter;

import com.sende.user.dao.dataobject.WorkspaceUserDO;
import com.sende.user.manager.bo.WorkspaceUserBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkspaceUserDoConverter {

    WorkspaceUserBO toBO(WorkspaceUserDO workspaceUserDO);

    WorkspaceUserDO toDO(WorkspaceUserBO workspaceUserBO);
}