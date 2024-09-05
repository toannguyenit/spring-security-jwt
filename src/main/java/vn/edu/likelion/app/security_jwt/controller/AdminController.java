package vn.edu.likelion.app.security_jwt.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // Chỉ cho phép người có vai trò ADMIN truy cập
@Slf4j
public class AdminController {

    // Ví dụ phương thức cho phép ADMIN lấy danh sách người dùng
    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Authenticated User: {}", username);


        return ResponseEntity.ok("List of users");
    }

}
