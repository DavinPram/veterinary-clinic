package com.enigma.veterinaryclinic.service;

import com.enigma.veterinaryclinic.dto.CategoryDTO;
import com.enigma.veterinaryclinic.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category save(Category category);
    Category getCategoryById(Integer id);
    Page<Category> listWithPage(Pageable pageable, CategoryDTO categoryDTO);
    Category update(Category category);
    String delete(Integer id);
}
