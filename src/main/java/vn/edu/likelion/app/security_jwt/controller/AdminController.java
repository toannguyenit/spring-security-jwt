package vn.edu.likelion.app.security_jwt.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // Chỉ cho phép người có vai trò ADMIN truy cập
public class AdminController {

    // Ví dụ phương thức cho phép ADMIN lấy danh sách người dùng
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok("List of users");
    }

}
