package com.security.spring_security.service;

import com.security.spring_security.dto.SaveUser;
import com.security.spring_security.persistence.entity.User;

public interface UserService {
    User createOneCostumer(SaveUser newUser);
}
