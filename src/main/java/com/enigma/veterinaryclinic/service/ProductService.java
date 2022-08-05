package com.enigma.veterinaryclinic.service;

import com.enigma.veterinaryclinic.dto.ProductDTO;
import com.enigma.veterinaryclinic.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product save(Product product);
    Product getProductById(String id);
    Page<Product> listWithPage(Pageable pageable, ProductDTO productDTO);
    Product update(Product product);
    String delete(String id);
    Page<Product> getAll(Pageable pageable, ProductDTO productDTO);
}
