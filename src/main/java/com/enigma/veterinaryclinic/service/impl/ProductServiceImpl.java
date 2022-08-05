package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.dto.ProductDTO;
import com.enigma.veterinaryclinic.entity.Product;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.ProductRepository;
import com.enigma.veterinaryclinic.service.CategoryService;
import com.enigma.veterinaryclinic.service.ProductService;
import com.enigma.veterinaryclinic.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Product save(Product product) {
        Product save = productRepository.save(product);
        if(categoryService.getCategoryById(save.getCategory().getId()).getDeletedAt() == null){
            return save;
        }else{
            throw new NotFoundException("Category not found");
        }
    }

    @Override
    public Product getProductById(String id) {
        if (findByIdOrThrowNotFound(id).getDeletedAt() == null) {
            return findByIdOrThrowNotFound(id);
        } else {
            throw new NotFoundException("Category was deleted");
        }
    }

    @Override
    public Page<Product> listWithPage(Pageable pageable, ProductDTO productDTO) {
        Specification<Product> specification = ProductSpecification.getSpecification(productDTO);
        List<Product> products = productRepository.findAll(specification,pageable).getContent();
        List<Product> newProducts = new ArrayList<>();
        for (Product product : products) {
            if(product.getDeletedAt() == null){
                newProducts.add(product);
            }
        }
        return new PageImpl<>(newProducts);
    }


    @Override
    public Product update(Product product) {
        findByIdOrThrowNotFound(product.getId());
        return productRepository.save(product);
    }

    @Override
    public String delete(String id) {
        Product product = findByIdOrThrowNotFound(id);

        Date setterDate = Calendar.getInstance().getTime();

        if (product.getDeletedAt() == null) {
            product.setDeletedAt(setterDate);
            productRepository.save(product);
        } else {
            throw new NotFoundException("Product was deleted");
        }
        return "Product id: " + id + " deleted";
    }

    @Override
    public Page<Product> getAll(Pageable pageable, ProductDTO productDTO) {
        Specification<Product> specification = ProductSpecification.getSpecification(productDTO);
        List<Product> products = productRepository.findAll(specification,pageable).getContent();
        List<Product> newProducts = new ArrayList<>();
        for (Product product : products) {
            if(product.getDeletedAt() == null && !product.getCategory().getName().equalsIgnoreCase("Product")){
                newProducts.add(product);
            }
        }
        return new PageImpl<>(newProducts);
    }

    private Product findByIdOrThrowNotFound(String id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new NotFoundException("Product not found!");
        }
    }
}
