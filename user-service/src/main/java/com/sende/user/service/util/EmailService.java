package com.sende.user.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 邮件发送服务（基于 Spring JavaMailSender）
 * <p>封装通用文本邮件发送；验证码邮件走 sendResetCode 单独通道。</p>
 */
@Component
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private EmailProperties emailProperties;

    /**
     * 发送密码重置验证码邮件
     */
    public void sendResetCode(String to, String code) {
        String subject = emailProperties.getResetSubject();
        String text = "您正在重置 Goods Insight 账号密码，验证码：\n\n" + code
                + "\n\n10 分钟内有效。如非本人操作，请忽略本邮件。";
        send(to, subject, text);
    }

    private void send(String to, String subject, String text) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(emailProperties.getFromName() + " <" + emailProperties.getFrom() + ">");
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);
            mailSender.send(msg);
            log.info("[Email] sent to={}, subject={}", to, subject);
        } catch (Exception e) {
            // 邮件发送失败不阻塞业务，但记录日志方便排查
            log.error("[Email] send failed: to={}, subject={}, err={}", to, subject, e.getMessage());
            throw new IllegalStateException("邮件发送失败: " + e.getMessage(), e);
        }
    }
}
