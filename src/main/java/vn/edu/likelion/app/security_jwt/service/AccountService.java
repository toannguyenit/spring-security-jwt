package vn.edu.likelion.app.security_jwt.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.likelion.app.security_jwt.entity.Account;

public interface AccountService extends UserDetailsService {
    Account save(Account account);
}
