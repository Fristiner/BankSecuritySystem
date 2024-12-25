package com.mt.utils;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    //    @Autowired
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String MailFrom;
    //    @Autowired
    private JavaMailSenderImpl mailSender;


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
     * 发送HTML格式邮件
     */
    public void sendHtmlEmail(String to, String subject, String htmlText) {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//        MimeMessageHelper mimeMessageHelper;
//
//        try {
//            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            //邮件发送人
//            mimeMessageHelper.setFrom(from);
//            //邮件接收人,设置多个收件人地址
//            InternetAddress[] internetAddressTo = InternetAddress.parse(to);
//            mimeMessageHelper.setTo(internetAddressTo);
//            //messageHelper.setTo(to);
//            //邮件主题
//            message.setSubject(subject);
//            //邮件内容，html格式
//            messageHelper.setText(content, true);
//            //发送
//            mailSender.send(message);
//            //日志信息
//            logger.info("邮件已经发送。");
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }

    }

    /**
     * 发送带附件的邮件
     */
    public void sendEmailWithAttachment(String to, String subject, String text, String filePath) {
    }
}