package com.phosa.cmas.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {

    private static final String SMTP_HOST = "smtp.qq.com";
    private static final String SMTP_PORT = "465";
    private static final String USERNAME = "2424518904@qq.com";
    private static final String PASSWORD = "kgwhszwrqdahdiag";

    @Autowired
    private Properties emailProperties;

    public void sendEmail(String to, String subject, String body) throws MessagingException {

        // 获取默认的Session对象
        Session session = Session.getInstance(emailProperties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailProperties.getProperty("username"), emailProperties.getProperty("password"));
            }
        });

        // 创建默认的MimeMessage对象
        Message message = new MimeMessage(session);

        // 设置发件人地址
        message.setFrom(new InternetAddress(emailProperties.getProperty("username")));

        // 设置收件人地址
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

        // 设置邮件主题
        message.setSubject(subject);

        // 设置邮件内容
        message.setText(body);

        // 发送邮件
        Transport.send(message);


    }

}

