package com.security.spring_security.service.auth;


import com.security.spring_security.dto.auth.AuthenticationRequest;
import com.security.spring_security.dto.auth.AuthenticationResponse;
import com.security.spring_security.dto.RegisterUser;
import com.security.spring_security.dto.SaveUser;
import com.security.spring_security.exception.ObjectNotFoundException;
import com.security.spring_security.persistence.entity.security.User;
import com.security.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisterUser registerCostumer(SaveUser newUser) {

        User user = userService.createOneCustumer(newUser);

        RegisterUser userDTO = new RegisterUser();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUserName(user.getUsername());
        userDTO.setRole(user.getRole().getName());

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        userDTO.setJwt(jwt);

        return userDTO;
    }

    private Map<String, Object> generateExtraClaims(User user){
        Map<String, Object> extraCliams = new HashMap<>();

        extraCliams.put("name", user.getName());
        extraCliams.put("role", user.getRole().getName());
        extraCliams.put("authorities", user.getAuthorities());

        return extraCliams;
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());

        authenticationManager.authenticate(authentication);

        User user = userService.findByUsername(authenticationRequest.getUsername()).get();

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        authenticationResponse.setJwt(jwt);

        return authenticationResponse;
    }

    public boolean validateToken(String jwt) {
        try {
            jwtService.extractUserName(jwt);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public User findLoggedUser(){
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) auth.getPrincipal();
        return userService.findByUsername(userName).orElseThrow(() -> new ObjectNotFoundException("User not found. Username: "+ userName ));
    }
}
