package com.phosa.cmas.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "mail.qq")
public class EmailConfig {

    @Value("${mail.qq.mail.smtp.auth}")
    private String auth;

    @Value("${mail.qq.mail.smtp.ssl.enable}")
    private String sslEnable;

    @Value("${mail.qq.mail.smtp.starttls.enable}")
    private String starttlsEnable;

    @Value("${mail.qq.mail.smtp.host}")
    private String host;

    @Value("${mail.qq.mail.smtp.port}")
    private String port;

    @Value("${mail.qq.username}")
    private String username;

    @Value("${mail.qq.password}")
    private String password;

    @Bean
    public Properties emailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.ssl.enable", sslEnable);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("username", username);
        props.put("password", password);
        return props;
    }
}
