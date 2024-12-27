package com.mt.utils;

import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    //    @Autowired
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String MailFrom;


    /**
     * 发送简单的文本邮件
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

    /**
     * @param to   目标邮箱
     * @param code 验证码
     * @return
     */
    public Boolean sendCodeToEmail(String to, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MailFrom);
            message.setTo(to);
            message.setSubject("验证码");
            message.setText("验证码" + code + "用于登录XX金融身份验证，3分钟内有效，请勿泄露和转发。如非本人操作，请忽略此信息");
            javaMailSender.send(message);
            logger.info("验证码已成功发送到邮箱：{}", to);
            return true;
        } catch (Exception e) {
            logger.error("发送验证码失败，目标邮箱: {}, 错误信息: {}", to, e.getMessage());
            return false;
        }
    }


    /**
     * 发送HTML格式邮件
     */
    public void sendHtmlEmail(String to, String subject, String htmlText) {
        RandomUtil.randomNumbers(6);
    }

    /**
     * 发送带附件的邮件
     */
    public void sendEmailWithAttachment(String to, String subject, String text, String filePath) {
    }
}