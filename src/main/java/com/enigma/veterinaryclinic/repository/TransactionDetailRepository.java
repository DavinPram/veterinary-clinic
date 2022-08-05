package com.enigma.veterinaryclinic.repository;

import com.enigma.veterinaryclinic.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail,String> {
    @Query("SELECT t FROM TransactionDetail t WHERE t.transaction = ?1")
    List<TransactionDetail> getByTransaction (String transactionId);

}
