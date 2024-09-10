package vn.edu.likelion.app.security_jwt.controller;

import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.likelion.app.security_jwt.dto.request.AuthenticationRequest;
import vn.edu.likelion.app.security_jwt.dto.request.IntrospectRequest;
import vn.edu.likelion.app.security_jwt.dto.response.AuthenticationResponse;
import vn.edu.likelion.app.security_jwt.dto.response.IntrospectResponse;
import vn.edu.likelion.app.security_jwt.service.AuthenticationService;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Login")
public class AuthenticationController {


    AuthenticationService authenticationService;
    @Operation(method = "POST", summary = "Login", description = "Send a request via this API to login by username and pass")
    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ResponseEntity.ok(result);
    }



    @PostMapping("/introspect")
    ResponseEntity<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ResponseEntity.ok().body(result);
    }
}