package lk.ijse.cleancopvt.controller;

import lk.ijse.cleancopvt.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendMail")
    public String sendMail() {
        emailService.sendEmail("receiver@example.com", "Test Subject", "Hello from Spring Boot!");
        return "Email sent!";
    }
}
