package controller;
import java.util.Properties;
import DataClasses.MailProfile;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 * Class to send an email
 */
public class SendMail {
    //Properties props= System.getProperties();
    Properties props = new Properties();

    /**
     * Class constructor
     */
    public SendMail() {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", MailProfile.HOST_NAME);
        props.put("mail.smtp.socketFactory.port", MailProfile.SSLPORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", MailProfile.SSLPORT);

    }

    /**
     * FUnction to send the email
     * @param userMail the email of the receiver
     */
    public void alert(String userMail) {
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getAuthentication() {
                return new PasswordAuthentication(MailProfile.EMAIL, MailProfile.PASSWORD);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userMail));
            message.setSubject("Monitor app");
            message.setText("Emergency! patient data above set threshold");

            // send message
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
