package vn.edu.likelion.app.security_jwt.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vn.edu.likelion.app.security_jwt.entity.Account;
import vn.edu.likelion.app.security_jwt.repository.AccountRepository;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    @Transactional
    public void sendHtmlEmail(String to, String subject, String message) throws MessagingException {
        Account account1 = accountRepository.findByEmail(to).orElseThrow(()->
                new UsernameNotFoundException("Account not found"));

        account1.setPassword(passwordEncoder.encode("123456"));
        accountRepository.save(account1);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        // Prepare the HTML content using Thymeleaf
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("message", message);
        String htmlContent = templateEngine.process("email/email-page", context);

        // Send email
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true indicates HTML

        mailSender.send(mimeMessage);
    }

    public void sendEmailWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        // Đính kèm file
        FileSystemResource file = new FileSystemResource(pathToAttachment);
        helper.addAttachment("Invoice", file);

        mailSender.send(mimeMessage);
    }
}
