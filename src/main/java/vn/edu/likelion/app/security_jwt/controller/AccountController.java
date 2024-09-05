package vn.edu.likelion.app.security_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.app.security_jwt.entity.Account;
import vn.edu.likelion.app.security_jwt.service.AccountService;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public Account register(@RequestBody Account account) {
        return accountService.save(account);
    }

    @GetMapping("/register")
    public String register2() {
        return "Dang ky thanh cong!";
    }


}
