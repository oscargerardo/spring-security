package com.security.spring_security.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.spring_security.dto.SaveProduct;
import com.security.spring_security.persistence.entity.Product;
import com.security.spring_security.service.ProductService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> findAll(Pageable pageable) {
        Page <Product> productsPage = productService.findAll(pageable);
        
        if(productsPage.hasContent()){
            return ResponseEntity.ok(productsPage);
        }
        return ResponseEntity.notFound().build();

    }


    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneById(@PathVariable Long productId) {
        Optional<Product> product = productService.findOneById(productId);
        
        if(product.isPresent()){
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();

    }


    @PostMapping
    public ResponseEntity<Product> createOne(@RequestBody @Valid SaveProduct saveProduct) {
        
        Product product = productService.createOne(saveProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }
    
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateOneById(@PathVariable Long productId, @RequestBody @Valid SaveProduct saveProduct) {
        
        Product product = productService.updateOneById(productId, saveProduct);

        return ResponseEntity.ok(product);

    }


    @PutMapping("/{productId}/disable")
    public ResponseEntity<Product> disableOneById(@PathVariable Long productId) {
        
        Product product = productService.disableOneById(productId);

        return ResponseEntity.ok(product);

    }
    
}
