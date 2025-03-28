package com.techzen.management.service;

import com.nimbusds.jose.JOSEException;
import com.techzen.management.dto.auth.AuthenticationRequest;
import com.techzen.management.dto.auth.AuthenticationResponse;
import com.techzen.management.dto.auth.IntrospectRequest;
import com.techzen.management.dto.auth.IntrospectResponse;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws ParseException, JOSEException;

}
