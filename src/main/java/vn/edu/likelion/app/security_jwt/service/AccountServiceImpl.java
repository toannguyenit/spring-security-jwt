package vn.edu.likelion.app.security_jwt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.likelion.app.security_jwt.entity.Account;
import vn.edu.likelion.app.security_jwt.repository.AccountRepository;


@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordStrengthService passwordStrengthService;


    @Override
    public Account save(Account account) {
        if (!passwordStrengthService.isPasswordStrength(account.getPassword())) {
            throw new RuntimeException("Mat khau khong du manh");
        };
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        log.info(account.toString());
        return accountRepository.save(account);
    }

    @Override
    public Account update(Long id, Account account) {
        Account account1 = accountRepository.findById(id).orElseThrow(()->
                new UsernameNotFoundException("Account not found"));

        if (!passwordStrengthService.isPasswordStrength(account.getPassword())) {
            throw new RuntimeException("Mat khau khong du manh");
        };

        account1.setPassword(passwordEncoder.encode(account.getPassword()));
        account1.setEmail(account.getEmail());
        return accountRepository.save(account1);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
        if (account.getIsDeleted()) {
            return null;
        }
        log.info(account.toString());
        return account;
    }
}
