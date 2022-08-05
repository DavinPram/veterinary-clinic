package com.enigma.veterinaryclinic.repository;

import com.enigma.veterinaryclinic.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findAll(Specification<Product> specification, Pageable pageable);

    @Query(value = "SELECT c FROM Product c WHERE c.category != 1")
    Product getAll();
}
