package com.security.spring_security.service;

import com.security.spring_security.dto.SaveUser;
import com.security.spring_security.persistence.entity.security.User;

import java.util.Optional;

public interface UserService {

    User createOneCustumer(SaveUser newUser);

    Optional<User> findByUsername(String userName);
}
