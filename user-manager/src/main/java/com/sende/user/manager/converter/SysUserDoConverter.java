package com.sende.user.manager.converter;

import com.sende.user.dao.dataobject.SysUserDO;
import com.sende.user.manager.bo.SysUserBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysUserDoConverter {

    SysUserBO toBO(SysUserDO sysUserDO);

    SysUserDO toDO(SysUserBO sysUserBO);
}