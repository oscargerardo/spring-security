package com.security.spring_security.controller;


import com.security.spring_security.dto.auth.AuthenticationRequest;
import com.security.spring_security.dto.auth.AuthenticationResponse;
import com.security.spring_security.service.auth.AuthenticateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticateService authenticateService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest){

        AuthenticationResponse rsp = authenticateService.login(authenticationRequest);
        return ResponseEntity.ok(rsp);
    }

}
