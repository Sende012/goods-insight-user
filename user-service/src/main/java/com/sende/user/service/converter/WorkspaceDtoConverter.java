package com.sende.user.service.converter;

import com.sende.user.api.dto.WorkspaceReqDTO;
import com.sende.user.api.dto.WorkspaceResDTO;
import com.sende.user.manager.bo.WorkspaceBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkspaceDtoConverter {

    WorkspaceBO toBO(WorkspaceReqDTO req);

    WorkspaceResDTO toDTO(WorkspaceBO bo);
}