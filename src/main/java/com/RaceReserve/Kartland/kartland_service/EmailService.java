package com.RaceReserve.Kartland.kartland_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")  // Inject the sender email from application.properties
    private String senderEmail;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    // Async method to send simple email messages (non-MIME)
    @Async
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            message.setFrom(senderEmail); // Use the configured sender email
            mailSender.send(message);
            logger.info("Email sent successfully to {}", to);
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    // Async method to send emails with custom body (non-MIME)
    @Async
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom(senderEmail); // Use the configured sender email
            mailSender.send(message);
            logger.info("Email sent successfully to {}", to);
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    // Async method to send contact message emails
    @Async
    public void sendContactMessage(String name, String userEmail, String message) {
        try {
            // Create a MIME message for more flexibility (like From header)
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            
            // Set the recipient's email (the owner's email)
            helper.setTo("s3458540@gmail.com");
            
            // Set the email subject
            helper.setSubject("New Contact Message from Kartland Website");

            // Set the email body with the provided user details
            String mailBody = "Name: " + name + "\n"
                            + "Email: " + userEmail + "\n"
                            + "Message:\n" + message;
            helper.setText(mailBody);

            // Set the From email to your service email
            helper.setFrom(senderEmail);  // Use the service email as From address
            
            // Set the Reply-To address to the user's email
            helper.setReplyTo(userEmail);  // User's email as Reply-To address
            
            // Send the email
            mailSender.send(mimeMessage);
            logger.info("Email sent successfully to {}", "s3458540@gmail.com");

        } catch (Exception e) {
            // Log any errors
            logger.error("Failed to send email: {}", e.getMessage());
        }
    }
}
