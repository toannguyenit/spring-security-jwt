package vn.edu.likelion.app.security_jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = false)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, unique = false)
    String email;

    @Column(nullable = false)
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
