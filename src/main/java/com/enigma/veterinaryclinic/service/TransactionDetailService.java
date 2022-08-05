package com.enigma.veterinaryclinic.service;

import com.enigma.veterinaryclinic.entity.TransactionDetail;

public interface TransactionDetailService {
    TransactionDetail create(TransactionDetail transactionDetail);
    TransactionDetail update(TransactionDetail transactionDetail);
}
