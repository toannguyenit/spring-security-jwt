package vn.edu.likelion.app.security_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.likelion.app.security_jwt.service.PasswordStrengthService;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    @Autowired
    private PasswordStrengthService passwordStrengthService;

    @PostMapping("/check")
    public String checkPassword(@RequestParam String password) {
        return passwordStrengthService.checkPasswordStrength(password);
    }
}