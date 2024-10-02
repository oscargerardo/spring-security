package com.security.spring_security.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.security.spring_security.dto.SaveProduct;
import com.security.spring_security.persistence.entity.Product;

import jakarta.validation.Valid;

public interface ProductService {

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findOneById(Long productId);

    Product createOne(@Valid SaveProduct saveProduct);

    Product updateOneById(Long productId, @Valid SaveProduct saveProduct);

    Product disableOneById(Long productId);

}
