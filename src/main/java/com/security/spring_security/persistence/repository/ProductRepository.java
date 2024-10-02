package com.security.spring_security.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.spring_security.persistence.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
