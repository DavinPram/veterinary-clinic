package com.enigma.veterinaryclinic.repository;

import com.enigma.veterinaryclinic.entity.Transaction;
import com.enigma.veterinaryclinic.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Page<Transaction> findAll(Specification<Transaction> specification, Pageable pageable);
}
