package com.techzen.management.controller;

import com.nimbusds.jose.JOSEException;
import com.techzen.management.dto.auth.AuthenticationRequest;
import com.techzen.management.dto.auth.AuthenticationResponse;
import com.techzen.management.dto.auth.IntrospectRequest;
import com.techzen.management.dto.auth.IntrospectResponse;
import com.techzen.management.service.IAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthenticationController {

    IAuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/introspect")
    public IntrospectResponse introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        return authenticationService.introspect(introspectRequest);
    }
}
