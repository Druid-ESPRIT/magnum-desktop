package com.druid.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/** This utility class implements a handy mail sending method. */
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

  /**
   * This method allows you to send emails. <br>
   * <br>
   *
   * <h2>Usage</h2>
   *
   * <pre>{@code
   * String recipient = "example@domain.org";
   * String subject = "Hello there";
   * String content = "What goes up must come down";
   * boolean html = false;
   *
   * Mail.send(recipient, subject, content, html);
   * }</pre>
   *
   * @param recipient The person who will be sent the mail.
   * @param subject The subject of the mail.
   * @param content The content of the mail.
   * @param html Whether the mail is an HTML document or plain text.
   */
  public static void send(String recipient, String subject, String content, boolean html) {
    try {
      String sender = Mail.username + '@' + Mail.provider;
      MimeMessage msg = new MimeMessage(getSession());

      msg.setFrom(sender);
      msg.setRecipients(Message.RecipientType.TO, recipient);
      msg.setSubject(subject);
      msg.setSentDate(new Date());
      if (html) {
        msg.setHeader("Content-Type", "text/html");
      }
      msg.setText(content);
      Transport.send(msg, sender, Mail.password);
    } catch (MessagingException ex) {
      ex.printStackTrace();
    }
  }

  public static void sendAttachement(String recipient, String subject, String content, boolean html)
      throws IOException {
    try {
      String sender = Mail.username + '@' + Mail.provider;
      MimeMessage msg = new MimeMessage(getSession());

      msg.setFrom(sender);
      msg.setRecipients(Message.RecipientType.TO, recipient);
      msg.setSubject(subject);
      msg.setSentDate(new Date());

      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(content);
      MimeBodyPart attachmentPart = new MimeBodyPart();
      attachmentPart.attachFile(new File("qr.png"));
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);
      multipart.addBodyPart(attachmentPart);

      msg.setContent(multipart);

      Transport.send(msg, sender, Mail.password);
    } catch (MessagingException ex) {
      ex.printStackTrace();
    }
  }
}
