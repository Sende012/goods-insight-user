package com.sende.user.dao.mapper;

import com.sende.user.dao.dataobject.PasswordResetCodeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PasswordResetCodeMapper {

    int insert(PasswordResetCodeDO row);

    /** 查某邮箱的最近一条未使用验证码（按创建时间倒序） */
    PasswordResetCodeDO selectLatestValid(@Param("email") String email);

    /** 标记已使用 */
    int markUsed(@Param("id") Long id);

    /** 清理过期记录 */
    int deleteExpired();
}
