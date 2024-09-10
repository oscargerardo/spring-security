package com.security.spring_security.service.auth;


import com.security.spring_security.dto.RegisterUser;
import com.security.spring_security.dto.SaveUser;
import com.security.spring_security.persistence.entity.User;
import com.security.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    public RegisterUser registerCostumer(SaveUser newUser) {

        User user = userService.createOneCostumer(newUser);

        RegisterUser userDTO = new RegisterUser();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUserName(user.getUsername());
        userDTO.setRol(user.getRole().name());

        String jwt = jwtService.generateToken(user);

        userDTO.setJwt(jwt);

        return userDTO;
    }
}
