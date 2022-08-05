package com.enigma.veterinaryclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionReportDTO {
    private String customer;
    private String doctor;
    private Integer totalPrice;
    private Date date;
    private String product;
    private Integer quantity;
    private Integer subtotal;
//    private List<TransactionDetailReportDTO> transactionDetailReportDTOS;
}
