package com.security.spring_security.service.impl;

import com.security.spring_security.dto.SaveUser;
import com.security.spring_security.exception.InvalidPasswordException;
import com.security.spring_security.persistence.entity.User;
import com.security.spring_security.persistence.repository.UserRepository;
import com.security.spring_security.persistence.util.Role;
import com.security.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User createOneCostumer(SaveUser newUser) {

        validatePassword(newUser);

        User user = new User();
        user.setName(newUser.getUserName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRole(Role.ROL_COSTUMER);
        user.setUsername(newUser.getUserName());

        return userRepository.save(user);
    }

    private void validatePassword(SaveUser newUser){

        if(!StringUtils.hasText(newUser.getPassword()) || !StringUtils.hasText(newUser.getRepitedPassword())){
            throw new InvalidPasswordException("Password don't match");
        }

        if(!newUser.getPassword().equals(newUser.getRepitedPassword())){
            throw new InvalidPasswordException("Password don't match");
        }

    }
}
