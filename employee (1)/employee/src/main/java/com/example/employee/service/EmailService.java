package com.example.employee.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String username) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);  //dynamically sends to the user's email
            helper.setSubject("Welcome to Our Platform!");
            helper.setText("Dear " + username + ",\n\nWelcome to our platform! We are excited to have you onboard.\n\nBest Regards,\nTeam", true);

            mailSender.send(message);
            System.out.println("Welcome email sent successfully!" + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
