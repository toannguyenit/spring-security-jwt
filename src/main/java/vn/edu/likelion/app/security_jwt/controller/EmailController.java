package vn.edu.likelion.app.security_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import vn.edu.likelion.app.security_jwt.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/send-email")
    public String sendEmail() {
        emailService.sendSimpleEmail("toannvs.it@gmail.com", "Test Subject", "Test email content");
        return "Email sent successfully!";
    }

    @PostMapping("/send-html-email")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String message) {
        try {
            emailService.sendHtmlEmail(to, subject, message);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();
        }
    }

    @PostMapping("/reset-password-html-email")
    public String resetPasswordEmail(@RequestParam String to) {
        String subject = "Reset Password";
        String message = "Reset password email is 123456";
        try {
            emailService.sendHtmlEmail(to, subject, message);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
}
