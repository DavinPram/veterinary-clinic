package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.entity.Transaction;
import com.enigma.veterinaryclinic.entity.TransactionDetail;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.TransactionDetailRepository;
import com.enigma.veterinaryclinic.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class TransactionDetailServiceImpl implements TransactionDetailService {

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Override
    public TransactionDetail create(TransactionDetail transactionDetail) {
        return transactionDetailRepository.save(transactionDetail);
    }

    @Override
    public TransactionDetail update(TransactionDetail transactionDetail) {
        findByIdOrThrowNotFound(transactionDetail.getId());
        return transactionDetailRepository.save(transactionDetail);
    }

    private TransactionDetail findByIdOrThrowNotFound(String id) {
        Optional<TransactionDetail> transactionDetail = this.transactionDetailRepository.findById(id);
        if (transactionDetail.isPresent()) {
            return transactionDetail.get();
        } else {
            throw new NotFoundException("Transaction detail not found!");
        }
    }
}
