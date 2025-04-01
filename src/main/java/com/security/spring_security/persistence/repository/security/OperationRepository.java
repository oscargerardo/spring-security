package com.security.spring_security.persistence.repository.security;

import com.security.spring_security.persistence.entity.security.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("SELECT o FROM Operation o WHERE o.permitAll = true")
    List<Operation> findByPublicAcces();
}
