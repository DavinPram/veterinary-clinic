package com.enigma.veterinaryclinic.payment;

import com.enigma.veterinaryclinic.entity.Animal;
import com.enigma.veterinaryclinic.entity.Customer;
import com.enigma.veterinaryclinic.entity.InPatient;
import com.enigma.veterinaryclinic.entity.User;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.service.AnimalService;
import com.enigma.veterinaryclinic.service.CustomerService;
import com.enigma.veterinaryclinic.service.InPatientService;
import com.enigma.veterinaryclinic.service.UserService;
import com.midtrans.Midtrans;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InpatientPayment {

  private String id;

  private Integer grossAmount;

  @Autowired
  private InPatientService inPatientService;

  @Autowired
  private AnimalService animalService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private UserService userService;

  public InpatientPayment(String id, Integer grossAmount) {
    this.id = id;
    this.grossAmount = grossAmount;
  }
}
