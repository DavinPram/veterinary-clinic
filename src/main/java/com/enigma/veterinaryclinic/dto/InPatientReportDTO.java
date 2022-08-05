package com.enigma.veterinaryclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InPatientReportDTO {
    private String doctor;
    private String operator;
    private String animal;
    private String cage;
    private String description;
    private Boolean payment;
    private Integer price;
    private Date checkoutAt;
    private String status;
    private Date createdAt;
}
