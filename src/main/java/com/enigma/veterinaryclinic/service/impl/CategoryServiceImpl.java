package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.dto.CategoryDTO;
import com.enigma.veterinaryclinic.entity.Category;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.CategoryRepository;
import com.enigma.veterinaryclinic.service.CategoryService;
import com.enigma.veterinaryclinic.specification.CategorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
//        List<Category> categories = categoryRepository.findAll();
//        for (Category list : categories) {
//            if(category.getName().equalsIgnoreCase(list.getName())){
//                throw new
//            }
//        }
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Integer id) {
        if (findByIdOrThrowNotFound(id).getDeletedAt() == null) {
            return findByIdOrThrowNotFound(id);
        } else {
            throw new NotFoundException("Category was deleted");
        }
    }

    @Override
    public Page<Category> listWithPage(Pageable pageable, CategoryDTO categoryDTO) {
        Specification<Category> specification = CategorySpecification.getSpecification(categoryDTO);
        List<Category> categories = categoryRepository.findAll(specification, pageable).getContent();
        List<Category> newCategories = new ArrayList<>();
        for (Category category : categories) {
            if (category.getDeletedAt() == null) {
                newCategories.add(category);
            }
        }
        return new PageImpl<>(newCategories);
    }

    @Override
    public Category update(Category category) {
        findByIdOrThrowNotFound(category.getId());
        return categoryRepository.save(category);
    }

    @Override
    public String delete(Integer id) {
        Category category = findByIdOrThrowNotFound(id);

        Date setterDate = Calendar.getInstance().getTime();

        if (category.getDeletedAt() == null) {
            category.setDeletedAt(setterDate);
            categoryRepository.save(category);
        } else {
            throw new NotFoundException("Category was deleted");
        }
        return "Category id: " + id + " deleted";
    }

    private Category findByIdOrThrowNotFound(Integer id) {
        Optional<Category> category = this.categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new NotFoundException("Category not found!");
        }
    }
}
