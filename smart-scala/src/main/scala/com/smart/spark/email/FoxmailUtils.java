package com.smart.spark.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by fc.w on 2017/3/31.
 */
public class FoxmailUtils {

    public static void main(String[] args) {
        sendEmail("aa", "bb");
    }

    public static void sendEmail(String appId, String appName) {
        String fromMail = "282189080@qq.com";
        final String userName = "282189080@qq.com";
        final String authCode = "dfwkckkehjtacbec";
        String[] to = {"wangfengchao@lifang.com"}; //"yusong@lifang.com", "fukuiwei@lifang.com"
        Properties props = new Properties();
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, authCode);
            }
        });
        try {
            InternetAddress[] sendTo = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                System.out.println("发送到:" + to[i]);
                sendTo[i] = new InternetAddress(to[i]);
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromMail));
            message.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo);
            message.setSubject("实时任务异常");
            message.setText("Spark程序异常退出： APP ID： " + appId + " APP Name: " + appName);
            message.saveChanges();
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
