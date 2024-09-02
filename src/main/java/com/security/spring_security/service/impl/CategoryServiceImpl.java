package com.security.spring_security.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.security.spring_security.dto.SaveCategory;
import com.security.spring_security.exception.ObjectNotFoundException;
import com.security.spring_security.persistence.entity.Category;
import com.security.spring_security.persistence.repository.CategoryRepository;
import com.security.spring_security.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findOneById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createOne(SaveCategory saveCategory) {
        
        Category category = new Category();

        category.setName(saveCategory.getName());
        category.setStatus(Category.CategoryStatus.ENABLED);

        return categoryRepository.save(category);
    }

    @Override
    public Category updateOneById(Long categoryId, SaveCategory saveCategory) {
        Category categoryStorage = categoryRepository.findById(categoryId).orElseThrow( () -> new ObjectNotFoundException("Category not found with id" + categoryId) );

        categoryStorage.setName(saveCategory.getName());

        return categoryRepository.save(categoryStorage);
    }

    @Override
    public Category disableOneById(Long categoryId) {
        Category categoryStorage = categoryRepository.findById(categoryId).orElseThrow( () -> new ObjectNotFoundException("Category not found with id" + categoryId) );

        categoryStorage.setStatus(Category.CategoryStatus.DISABLED);

        return categoryRepository.save(categoryStorage);
    }
    
}
