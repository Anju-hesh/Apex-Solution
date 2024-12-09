package com.ijse.apexbuildingsolution.apex_building_solution.service;

import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class SendEmail {
    public void sendEmailWithGmail(String fromEmail, String toEmail, String subject, String body) {
        final String PASSWORD = System.getenv("EMAIL_PASSWORD");
        System.out.println("Retrieved Password: " + PASSWORD);
        if (PASSWORD == null || PASSWORD.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Email password not configured").show();
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, PASSWORD);
         }
    });

        try {
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully").show();
        } catch (AddressException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid email address").show();
            e.printStackTrace();
        } catch (MessagingException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to send email").show();
            e.printStackTrace();
        }
    }
}
