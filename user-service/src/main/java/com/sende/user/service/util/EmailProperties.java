package com.sende.user.service.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件发送配置（绑定 spring.mail.* 与 app.email.from）
 */
@Component
@ConfigurationProperties(prefix = "app.email")
public class EmailProperties {

    /** 发件人邮箱（与 spring.mail.username 一致） */
    private String from;

    /** 发件人显示名 */
    private String fromName = "Goods Insight";

    /** 邮件主题模板 */
    private String resetSubject = "【Goods Insight】密码重置验证码";

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getFromName() { return fromName; }
    public void setFromName(String fromName) { this.fromName = fromName; }
    public String getResetSubject() { return resetSubject; }
    public void setResetSubject(String resetSubject) { this.resetSubject = resetSubject; }
}
