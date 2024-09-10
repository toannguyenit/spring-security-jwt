package vn.edu.likelion.app.security_jwt.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.app.security_jwt.dto.request.AuthenticationRequest;
import vn.edu.likelion.app.security_jwt.dto.response.AuthenticationResponse;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Home")
public class HomeController {

    @GetMapping("/welcome")
    ResponseEntity<Object> welcome(){
        return ResponseEntity.ok("welcome");
    }

}
