package com.security.spring_security.controller;

import com.security.spring_security.dto.RegisterUser;
import com.security.spring_security.dto.SaveUser;
import com.security.spring_security.service.auth.AuthenticateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/costumers")
public class CostumerController {

    @Autowired
    private AuthenticateService authenticateService;

    @PostMapping
    public ResponseEntity<RegisterUser> regirterOne (@RequestBody @Valid SaveUser newUser){
        RegisterUser registerUser = authenticateService.registerCostumer(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }

}
