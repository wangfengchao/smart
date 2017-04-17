package com.smart.spark.email;

import com.smart.spark.utils.ConfigProperties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by fc.w on 2017/3/31.
 */
public class FoxmailUtils {

    public static void sendEmail(String appName) {
        String[] receiverEmail = ConfigProperties.getString("receiver_email").split(",");
        Properties props = new Properties();
        props.setProperty(ConfigProperties.getString("smtp_ssl_param"), ConfigProperties.getString("smtp_ssl"));
        props.setProperty(ConfigProperties.getString("smtp_host_param"), ConfigProperties.getString("smtp_host"));
        props.setProperty(ConfigProperties.getString("smtp_port_param"), ConfigProperties.getString("smtp_port"));
        props.setProperty(ConfigProperties.getString("smtp_auth_param"), ConfigProperties.getString("smtp_auth"));

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ConfigProperties.getString("server_email"), ConfigProperties.getString("auth_code"));
            }
        });

        try {
            InternetAddress[] sendTo = new InternetAddress[receiverEmail.length];
            for (int i = 0; i < receiverEmail.length; i++) {
                sendTo[i] = new InternetAddress(receiverEmail[i]);
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ConfigProperties.getString("server_email")));
            message.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo);
            message.setSubject("SparkStreaming任务异常");
            message.setText("SparkStreaming程序异常退出：  APP Name: " + appName);
            message.saveChanges();
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
