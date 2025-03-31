package com.security.spring_security.service.impl;

import com.security.spring_security.dto.SaveUser;
import com.security.spring_security.exception.InvalidPasswordException;
import com.security.spring_security.exception.ObjectNotFoundException;
import com.security.spring_security.persistence.entity.security.Role;
import com.security.spring_security.persistence.entity.security.User;
import com.security.spring_security.persistence.repository.security.UserRepository;
import com.security.spring_security.service.RoleService;
import com.security.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;


    @Override
    public User createOneCustumer(SaveUser newUser) {

        validatePassword(newUser);

        User user = new User();
        user.setName(newUser.getUserName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));


        Role defaultRole = roleService.findDefaultRole()
                            .orElseThrow(()-> new ObjectNotFoundException("Role not found. Default role"));

        user.setRole(defaultRole);
        user.setUsername(newUser.getUserName());

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    private void validatePassword(SaveUser newUser){

        if(!StringUtils.hasText(newUser.getPassword()) || !StringUtils.hasText(newUser.getRepetedPassword())){
            throw new InvalidPasswordException("Password don't match");
        }

        if(!newUser.getPassword().equals(newUser.getRepetedPassword())){
            throw new InvalidPasswordException("Password don't match");
        }

    }
}
