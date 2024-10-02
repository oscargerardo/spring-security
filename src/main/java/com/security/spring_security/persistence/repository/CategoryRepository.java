package com.security.spring_security.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.spring_security.persistence.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
