package com.enigma.veterinaryclinic.service;

import com.enigma.veterinaryclinic.dto.QueueDTO;
import com.enigma.veterinaryclinic.dto.TransactionDTO;
import com.enigma.veterinaryclinic.entity.Transaction;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;

public interface TransactionService {
    Transaction transaction(Transaction transaction);
    Transaction getTransactionById(String id);
    Transaction update(Transaction transaction);
    Transaction payment(String id);
    Page<Transaction> listWithPage(Pageable pageable, TransactionDTO transactionDTO);
    Page<Transaction> queue(Pageable pageable, QueueDTO queueDTO);
    String exportReport(String reportFormat) throws FileNotFoundException, JRException;
}
