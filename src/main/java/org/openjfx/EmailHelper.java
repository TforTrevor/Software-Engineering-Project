package org.openjfx;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class EmailHelper {
    private ArrayList<InternetAddress> to;
    private ArrayList<File> images;
    private String from;
    private String subject;
    private String body;
    private String host;
    private Properties props;
    private Session session;

    public EmailHelper() {
        to = new ArrayList<>();
        from = "oldtimerimagefinder@gmail.com";
        host = "smtp.gmail.com";
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
        try {
            to.add(new InternetAddress(recipient));
        } catch (AddressException e) {
            e.printStackTrace();
        }
    }

    public boolean VerifyEmail(String address) {
        boolean isValid = false;
        try {
            InternetAddress newAddress = new InternetAddress(address);
            newAddress.validate();
            isValid = true;
        } catch (AddressException aex) {
            System.out.println("Invalid email");
        }
        return isValid;
    }

    public void ClearRecipients() {
        to.clear();
    }

    public void SetBody(String message) {
        body = message;
    }

    public void SetSubject(String subject) {
        this.subject = subject;
    }

    public void SendEmail() {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for (InternetAddress address : to) {
                message.addRecipient(Message.RecipientType.TO, address);
            }
            message.setSubject(subject);
            message.setSentDate(new Date());

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

//            for (File f : images) {
//                BodyPart attachmentBodyPart = new MimeBodyPart();
//                //DataSource image = new FileDataSource("C:\\Users\\godbo\\IdeaProjects\\Software-Engineering-Project\\src\\main\\resources\\ani2.png");
//                DataSource image = new FileDataSource(f);
//                attachmentBodyPart.setDataHandler(new DataHandler(image));
//                attachmentBodyPart.setFileName("sheenLUL.png");
//                multipart.addBodyPart(attachmentBodyPart);
//            }

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
