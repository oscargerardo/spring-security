package com.security.spring_security.service.impl;

import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.security.spring_security.dto.SaveProduct;
import com.security.spring_security.persistence.entity.Product;
import com.security.spring_security.service.ProductService;

import jakarta.validation.Valid;

@Service
public class ProductServiceImpl implements ProductService{

    //@Autowired
    //private ProductRepository ProductRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Product> findOneById(Long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOneById'");
    }

    @Override
    public Product createOne(@Valid SaveProduct saveProduct) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createOne'");
    }

    @Override
    public Product updateOneById(Long productId, @Valid SaveProduct saveProduct) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOneById'");
    }

    @Override
    public Product disableOneById(Long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableOneById'");
    }
    
}
