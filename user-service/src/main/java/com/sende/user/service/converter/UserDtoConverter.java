package com.sende.user.service.converter;

import com.sende.user.api.dto.UserReqDTO;
import com.sende.user.api.dto.UserResDTO;
import com.sende.user.manager.bo.SysUserBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoConverter {

    SysUserBO toBO(UserReqDTO req);

    UserResDTO toDTO(SysUserBO bo);
}