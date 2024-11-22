package org.example;

import java.io.File;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMailBySite {
    public static void main(String[] args) {
        String host = "smtp.gmail.com";
        final String user = "xxx"; // replace with your email
        final String password = "xxx"; // replace with app password
        List<String> recipients = List.of("xxx", "xxx"); //change accordingly
        // Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS
        props.put("mail.smtp.port", "587"); // Port for TLS

        String htmlContent = "<h1>Email</h1><p>Sent using JavaMail</p>";
        List<File> attachments = List.of(
                new File("D:\\java\\JavaMail\\src\\main\\java\\org\\example\\txt1.txt"),
                new File("D:\\java\\JavaMail\\src\\main\\java\\org\\example\\txt2.txt"));


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        try {
            // Compose the message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }
            message.setSubject("HTML Email with Attachments");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlContent, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (attachments != null) {
                for (File file : attachments) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(file);
                    multipart.addBodyPart(attachmentPart);
                }
            }

            message.setContent(multipart);


            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully to " + recipients.size() + " recipient(s).");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email.");
        }
    }
}
