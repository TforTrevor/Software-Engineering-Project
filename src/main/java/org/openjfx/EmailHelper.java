package org.openjfx;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class EmailHelper {
    private ArrayList<String> to;
    private String from;
    private String subject;
    private String body;
    private String host;
    private Properties props;
    private Session session;
    public EmailHelper() {
        to = new ArrayList<>();
        from="oldtimerimagefinder@gmail.com";
        host="smtp.gmail.com";
        props = System.getProperties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "DasInDiabloNotDestiny");
            }
        });
    }
    public void AddRecipient(String recipient) {
        to.add(recipient);
    }
    public void ClearRecipients() {
        to.clear();
    }
    public void SetBody(String message) {
        body=message;
    }
    public void SetSubject(String subject) {
        this.subject=subject;
    }
    public void SendEmail() {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for (String s: to) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(s));
            }
            message.setSubject(subject);
            message.setSentDate(new Date());

            BodyPart messageBodyPart = new MimeBodyPart();
            ((MimeBodyPart) messageBodyPart).setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            BodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource image = new FileDataSource("C:\\Users\\godbo\\IdeaProjects\\Software-Engineering-Project\\src\\main\\resources\\ani2.png");
            attachmentBodyPart.setDataHandler(new DataHandler(image));
            attachmentBodyPart.setFileName("sheenLUL.png");
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
