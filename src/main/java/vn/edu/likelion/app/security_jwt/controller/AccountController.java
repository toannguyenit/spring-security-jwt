package vn.edu.likelion.app.security_jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.app.security_jwt.entity.Account;
import vn.edu.likelion.app.security_jwt.service.AccountService;

@RestController
@RequestMapping("api/v1/account")
@Tag(name = "Auth")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(method = "POST", summary = "Add new user", description = "Send a request via this API to create new user")
    @PostMapping("/register")
    public Account register(@RequestBody Account account) {
        return accountService.save(account);
    }

    @Operation(method = "POST", summary = "Add new user", description = "Send a request via this API to create new user")
    @GetMapping("/register")
    public String register2() {
        return "Dang ky thanh cong!";
    }


}
