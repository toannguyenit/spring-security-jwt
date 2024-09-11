package vn.edu.likelion.app.security_jwt.service;

import org.springframework.stereotype.Service;

@Service
public class PasswordStrengthService {

    public String checkPasswordStrength(String password) {
        int score = 0;

        // Kiểm tra độ dài mật khẩu
        if (password.length() >= 8) score++;
        if (password.length() >= 12) score++;

        // Kiểm tra có chứa chữ thường
        if (password.matches(".*[a-z].*")) score++;

        // Kiểm tra có chứa chữ hoa
        if (password.matches(".*[A-Z].*")) score++;

        // Kiểm tra có chứa số
        if (password.matches(".*\\d.*")) score++;

        // Kiểm tra có chứa ký tự đặc biệt
        if (password.matches(".*[!@#$%^&*()\\-_=+{};:,<.>].*")) score++;

        // Đánh giá độ mạnh yếu
        if (score <= 2) {
            return "Weak";
        } else if (score <= 4) {
            return "Medium";
        } else {
            return "Strong";
        }
    }

    public boolean isPasswordStrength(String password) {
        return checkPasswordStrength(password).equals("Strong");
    }
}