package com.druid.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Mail {
    private static String username = "devel.magnum";
    private static String provider = "gmail.com";
    private static String smtpHost = "smtp.gmail.com";
    private static String password = "soooosjrgtguwowg";

    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.user", Mail.username);
        props.put("mail.smtp.host", Mail.smtpHost);
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "false");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable", "true");
        props.put("mail.smtp.ssl.checkserveridentity", "true");
        return Session.getInstance(props, null);
    }

    public static void send(String recipient, String subject, String content, boolean html) {
        try {
            String sender = Mail.username + '@' + Mail.provider;
            MimeMessage msg = new MimeMessage(getSession());

            msg.setFrom(sender);
            msg.setRecipients(Message.RecipientType.TO, recipient);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            if (html) msg.setHeader("Content-Type", "text/html");
            msg.setText(content);
            Transport.send(msg, sender, Mail.password);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
