package com.enigma.veterinaryclinic.repository;

import com.enigma.veterinaryclinic.entity.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator,String> {
    Page<Operator> findAll(Specification<Operator> specification, Pageable pageable);
}
