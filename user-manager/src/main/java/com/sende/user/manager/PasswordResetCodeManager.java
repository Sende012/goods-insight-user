package com.sende.user.manager;

import com.sende.user.dao.dataobject.PasswordResetCodeDO;
import com.sende.user.dao.mapper.PasswordResetCodeMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 密码重置验证码事务层
 */
@Component
public class PasswordResetCodeManager {

    /** 验证码有效期（分钟） */
    public static final int EXPIRE_MINUTES = 10;

    @Resource
    private PasswordResetCodeMapper passwordResetCodeMapper;

    @Transactional(rollbackFor = Exception.class)
    public Long create(String email, String code) {
        Date now = new Date();
        Date expire = new Date(now.getTime() + EXPIRE_MINUTES * 60_000L);
        PasswordResetCodeDO row = new PasswordResetCodeDO();
        row.setEmail(email);
        row.setCode(code);
        row.setExpiresAt(expire);
        row.setUsed(0);
        row.setCreatedTime(now);
        passwordResetCodeMapper.insert(row);
        return row.getId();
    }

    public PasswordResetCodeDO findLatestValid(String email) {
        return passwordResetCodeMapper.selectLatestValid(email);
    }

    @Transactional(rollbackFor = Exception.class)
    public int markUsed(Long id) {
        return passwordResetCodeMapper.markUsed(id);
    }
}
