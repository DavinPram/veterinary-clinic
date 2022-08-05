package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.dto.QueueDTO;
import com.enigma.veterinaryclinic.dto.TransactionDTO;
import com.enigma.veterinaryclinic.entity.Transaction;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.service.TransactionService;
import com.enigma.veterinaryclinic.util.WebResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@SecurityRequirement(name = "vetclinicapi")
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<WebResponse<Transaction>> createTransaction(@RequestBody Transaction transaction) {
        Transaction purchase = transactionService.transaction(transaction);
        WebResponse<Transaction> response = new WebResponse<>("Transaction success", purchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getById(@PathVariable("transactionId") String id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }

    @GetMapping("/report/{format}")
    public ResponseEntity<com.enigma.veterinaryclinic.response.WebResponse<String>> generatedReport(@PathVariable String format) throws JRException, FileNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(new com.enigma.veterinaryclinic.response.WebResponse<>(String.format("Download Success"),transactionService.exportReport(format)));
    }

    @PutMapping("/payment/{transactionId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<Transaction>> payment(@PathVariable("transactionId") String id) {
        Transaction transaction = transactionService.payment(id);
        WebResponse<Transaction> response = new WebResponse<>("Payment success!", transaction);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<Transaction>> update(@RequestBody Transaction transaction) {
        Transaction update = transactionService.update(transaction);
        WebResponse<Transaction> response = new WebResponse<>("Update success!", update);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Transaction>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "paymentStatus", required = false) Boolean paymentStatus) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        TransactionDTO transactionDTO = new TransactionDTO(paymentStatus);
        Page<Transaction> transactions = this.transactionService.listWithPage(pageable, transactionDTO);
        PageResponse<Transaction> pageResponse = new PageResponse<>(
                transactions.getContent(),
                transactions.getTotalElements(),
                transactions.getTotalPages(),
                page,
                size,
                sortBy
        );

        WebResponse<PageResponse<Transaction>> webResponse =
                new WebResponse<>("Data has been retrieved", pageResponse);

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

    @GetMapping("/queue")
    public ResponseEntity<WebResponse<PageResponse<Transaction>>> queue(
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "date", required = false) String date) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        QueueDTO queueDTO = new QueueDTO(date);
        Page<Transaction> transactions = this.transactionService.queue(pageable, queueDTO);
        PageResponse<Transaction> pageResponse = new PageResponse<>(
                transactions.getContent(),
                transactions.getTotalElements(),
                transactions.getTotalPages(),
                page,
                size,
                sortBy
        );

        WebResponse<PageResponse<Transaction>> webResponse =
                new WebResponse<>("Data has been retrieved", pageResponse);

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

}
