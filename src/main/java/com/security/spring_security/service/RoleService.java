package com.security.spring_security.service;


import com.security.spring_security.persistence.entity.security.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findDefaultRole();
}
