package com.security.spring_security.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.security.spring_security.dto.SaveProduct;
import com.security.spring_security.exception.ObjectNotFoundException;
import com.security.spring_security.persistence.entity.Category;
import com.security.spring_security.persistence.entity.Product;
import com.security.spring_security.persistence.repository.ProductRepository;
import com.security.spring_security.service.ProductService;

import jakarta.validation.Valid;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findOneById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product createOne(@Valid SaveProduct saveProduct) {
        
        Product product = new Product();

        product.setName(saveProduct.getName());
        product.setPrice(saveProduct.getPrice());
        product.setStatus(Product.ProductStatus.ENABLED);
        
        Category category = new Category();
        category.setId(saveProduct.getCategoryId());
        
        product.setCategory(category);

        productRepository.save(product);

        return product;
    }

    @Override
    public Product updateOneById(Long productId, @Valid SaveProduct saveProduct) {
        
        Product productStorage = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException("Product not found with id " + productId));

        productStorage.setName(saveProduct.getName());
        productStorage.setPrice(saveProduct.getPrice());
        
        Category category = new Category();
        category.setId(saveProduct.getCategoryId());
        
        productStorage.setCategory(category);

        productRepository.save(productStorage);

        return productStorage;

    }

    @Override
    public Product disableOneById(Long productId) {
        
        Product productStorage = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException("Product not found with id " + productId));

        productStorage.setStatus(Product.ProductStatus.DISABLED);

        productRepository.save(productStorage);

        return productStorage;
    }
    
}
