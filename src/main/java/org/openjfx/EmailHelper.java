package org.openjfx;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.text.html.ImageView;
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
    private Session session;

    public EmailHelper() {
        images = new ArrayList<>();
        //images.add(new File("D:\\SteamLibrary\\steamapps\\common\\Tom Clancy's Rainbow Six Siege\\datapc64_merged_bnk_textures3.forge"));
        to = new ArrayList<>();
        from = "oldtimerimagefinder@gmail.com";
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
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
        } catch (AddressException aex) {}
        return isValid;
    }

    public void ClearAll() {
        to.clear();
        images.clear();
    }

    public void SetBody(String message) {
        body = message;
    }

    public void SetSubject(String subject) {
        this.subject = subject;
    }

    public void setImages(ArrayList<ImageViewerImage> images) {
        this.images.clear();
        for (ImageViewerImage image: images) {
            this.images.add(new File(image.GetXMLImage().GetPath()));
        }
    }

    public void SendEmail() {
        try {
            int imagesAttached = 0;
            int imageBytes=0;
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

            for (File f : images) {
                imageBytes += f.length();
                System.out.println("IMAGE LENGTH: "+f.length());
                //Max gmail size is 25 MB, rounded to 1000000B=1MB to account for encoding and convenience
                if (imageBytes < 25000000) {
                    imagesAttached+=1;
                    System.out.println(imagesAttached);
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource image = new FileDataSource(f);
                    attachmentBodyPart.setDataHandler(new DataHandler(image));
                    attachmentBodyPart.setFileName(f.getName());
                    multipart.addBodyPart(attachmentBodyPart);
                }
                else {
                    break;
                }
            }
            for (int i=0;i<imagesAttached;i++) {
                images.remove(0);
            }
            message.setContent(multipart);

            System.out.println("Sent successfully");
            if (imagesAttached>0) {
                Transport.send(message);
            }
            else {
                images.remove(0);
            }
            if (images.size()>0) {
                SendEmail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
