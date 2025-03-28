package com.techzen.management.service.impl;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.techzen.management.dto.auth.AuthenticationRequest;
import com.techzen.management.dto.auth.AuthenticationResponse;
import com.techzen.management.dto.auth.IntrospectRequest;
import com.techzen.management.dto.auth.IntrospectResponse;
import com.techzen.management.enums.ErrorCode;
import com.techzen.management.exception.ApiException;
import com.techzen.management.model.User;
import com.techzen.management.service.IAuthenticationService;
import com.techzen.management.repository.IUserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import static org.apache.catalina.realm.UserDatabaseRealm.getRoles;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService implements IAuthenticationService {
    IUserRepository userRepository;

    @Value("${jwt.signerKey}")
    @NonFinal
    private String SIGNER_KEY;

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByUsername(authenticationRequest.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if(user == null || !passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorCode.UNAUTHENTICATION);
        }
        return AuthenticationResponse.builder()
                .token(generateToken(user))
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        return IntrospectResponse.builder()
                .isValid(verifyJWT(introspectRequest.getToken()))
                .build();
    }


    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername()) // Đặt chủ thể (subject) của JWT là tên đăng nhập của người dùng
                .issuer("sqc.com") // Đặt người phát hành JWT là "sqc.com"
                .issueTime(new Date()) // Đặt thời gian phát hành JWT là thời điểm hiện tại
                .expirationTime(new Date( // Đặt thời gian hết hạn cho JWT là 1 giờ kể từ lúc phát hành
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                // Thêm một custom claim (thông tin tùy chỉnh) vào JWT, chứa thông tin về đối tượng Student
                .claim("scope", getRoles(user))
                .build(); // Xây dựng đối tượng JWTClaimsSet

        // Tạo payload từ claims đã tạo, chuyển đối tượng claims thành định dạng JSON
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // Tạo JWSObject từ header và payload, kết hợp chúng lại thành đối tượng JWS
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            // Ký JWT bằng thuật toán HMAC SHA-512, sử dụng khóa bí mật (SIGNER_KEY)
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

            // Chuyển đối tượng JWS thành chuỗi JWT hoàn chỉnh (header.payload.signature) và trả về
            return jwsObject.serialize();
        } catch (JOSEException e) {
            // Nếu có lỗi xảy ra trong quá trình ký JWT, ném ra ngoại lệ RuntimeException
            throw new RuntimeException(e);
        }
    }

    private String getRoles(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        user.getRoles().forEach(role ->
                stringJoiner.add(role.getName()));
        return stringJoiner.toString();
    }

    public boolean verifyJWT(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiry = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return verified && expiry.after(new Date());
    }
}
