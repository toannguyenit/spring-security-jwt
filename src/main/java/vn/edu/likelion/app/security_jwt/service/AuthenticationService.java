package vn.edu.likelion.app.security_jwt.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.likelion.app.security_jwt.dto.request.AuthenticationRequest;
import vn.edu.likelion.app.security_jwt.dto.request.IntrospectRequest;
import vn.edu.likelion.app.security_jwt.dto.response.AuthenticationResponse;
import vn.edu.likelion.app.security_jwt.dto.response.IntrospectResponse;
import vn.edu.likelion.app.security_jwt.repository.AccountRepository;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @NonFinal
    @Value("${jwt.secretKey}")
    protected String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder().valid(verified && expiryTime.after(new Date())).build();
    }

//    public AuthenticationResponse authenticate(AuthenticationRequest request){
    // Tao mau test
//        String username = request.getUsername();
//        String password = request.getPassword();
//        String account_name = "admin";
//        String account_pass = "admin";
//        String account_role = "ADMIN";

//        if (account_pass.equals(password) && username.equals(account_name)) {
//            var token = generateToken(request.getUsername(), user.getRole());
//            return AuthenticationResponse.builder()
//                    .token(token)
//                    .authenticated(true)
//                    .build();
//        }
//        else {
//            throw new BadCredentialsException("Invalid username or password");
//        }
//    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.getUsername()));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        // Mã hóa mật khẩu người dùng nhập vào để so sánh
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Log mật khẩu người dùng nhập vào (chưa mã hóa)
        log.info("Mật khẩu gốc (người dùng nhập): " + request.getPassword());

        // Log mật khẩu người dùng nhập vào ( mã hóa)
        log.info("Mật khẩu mã hoá (người dùng nhập): " + encodedPassword);

        // Log mật khẩu đã mã hóa từ cơ sở dữ liệu
        log.info("Mật khẩu mã hóa (trong DB): " + user.getPassword());


        if (!authenticated) throw new BadCredentialsException("Invalid username or password");

        // Generate token
        var token = generateToken(request.getUsername(), user.getRole());

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    public String generateToken(String username, String role) {

        // Tao thong tin Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Tao thong tin Object
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().subject(username) // Thong tin user dang nhap
                .issuer("vn.edu.likelion.app") // Domain cua ung dung
                .issueTime(new Date()).expirationTime(new Date(

                        //new Date().getTime() + 1000 * 60 * 60 * 24) // Thoi gian 1 ngay
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("customClaim", "customClaimValue").claim("role", role).build();

        // Set Payload tu object
        Payload payload = new Payload(jwtClaimsSet.toJSONObject()); // Tao pa

        // Tao ra du lieu
        JWSObject jwsObject = new JWSObject(header, payload);

        // Tao chu ky
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Failed to generate token", e);
            throw new RuntimeException(e);
        }

    }
}
