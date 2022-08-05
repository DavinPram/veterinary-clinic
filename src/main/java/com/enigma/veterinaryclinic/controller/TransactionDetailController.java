package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.entity.TransactionDetail;
import com.enigma.veterinaryclinic.service.TransactionDetailService;
import com.enigma.veterinaryclinic.util.WebResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "vetclinicapi")
@RequestMapping("/transactionDetails")
public class TransactionDetailController {

    @Autowired
    TransactionDetailService transactionDetailService;

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<TransactionDetail>> update(@RequestBody TransactionDetail transactionDetail){
        TransactionDetail update = transactionDetailService.update(transactionDetail);
        WebResponse<TransactionDetail> response = new WebResponse<>("Update success!", update);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
