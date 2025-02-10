package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.model.PasswordResetToken;
import com.example.employee.repository.EmpRepo;
import com.example.employee.repository.ForgotPasswordRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmpRepo empRepo;

    @Autowired
    private ForgotPasswordRepo forgotPasswordRepo;

    // Send Reset Password Email
    public void sendPasswordResetEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email cannot be empty");
        }

        Employee employee = empRepo.findByEmail(email);
        if (employee == null) {
            throw new RuntimeException("User not found");
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, employee);
        forgotPasswordRepo.save(resetToken);

        String resetLink = "http://localhost:3000/reset-password?token=" + token;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Password Reset Request");
            helper.setText("<p>Click the link to reset your password:</p>" +
                    "<p><a href=\"" + resetLink + "\">Reset Password</a></p>", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email", e);
        }
    }

    // Reset Password
    public void resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> optionalResetToken = forgotPasswordRepo.findByToken(token);

        if (optionalResetToken.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        PasswordResetToken resetToken = optionalResetToken.get();
        if (resetToken.isExpired()) {
            forgotPasswordRepo.delete(resetToken);
            throw new RuntimeException("Token expired");
        }

        Employee employee = resetToken.getEmployee();
        employee.setPassword(hashPassword(newPassword)); // Hash password before saving
        empRepo.save(employee);
        forgotPasswordRepo.delete(resetToken);
    }

    // Password Hashing (SHA-256)
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
