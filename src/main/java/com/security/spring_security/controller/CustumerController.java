package com.security.spring_security.controller;

import com.security.spring_security.dto.RegisterUser;
import com.security.spring_security.dto.SaveUser;
import com.security.spring_security.persistence.entity.security.User;
import com.security.spring_security.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/custumers")
public class CustumerController {

    @Autowired
    private AuthenticationService authenticationService;


    @PreAuthorize("permitAll")
    @PostMapping
    public ResponseEntity<RegisterUser> registerOne(@RequestBody @Valid SaveUser newUser){
        RegisterUser registerUser = authenticationService.registerCostumer(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }


    @PreAuthorize("denyAll")
    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(Arrays.asList());
    }
}
