package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.entity.*;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.payment.ConfigPayment;
import com.enigma.veterinaryclinic.repository.InPatientRepository;
import com.enigma.veterinaryclinic.repository.TransactionRepository;
import com.enigma.veterinaryclinic.service.*;
import com.midtrans.Midtrans;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

  @Autowired
  private InPatientService inPatientService;

  @Autowired
  private InPatientRepository inPatientRepository;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private UserService userService;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private TransactionRepository transactionRepository;


  @Override
  public List<String> paymentInpatient(String inpatientId, Integer grossAmount) throws MidtransError {
    ConfigPayment configPayment = new ConfigPayment();

    List<String> responses = new ArrayList<>();

    InPatient getInpatientById = inPatientService.getById(inpatientId);

    responses.add("process");
    responses.add(configPayment.getClientKey());

    String snapToken = SnapApi.createTransactionToken(inpatientRequestBody(inpatientId, grossAmount, configPayment.getServerKey()));
    responses.add(snapToken);

    getInpatientById.setStatus("process");
    inPatientRepository.save(getInpatientById);

    return responses;
  }

  @Override
  public List<String> paymentTransaction(String transactionId, Integer grossAmount) throws MidtransError {
    ConfigPayment configPayment = new ConfigPayment();

    List<String> responses = new ArrayList<>();

    Transaction getTransactionById = transactionService.getTransactionById(transactionId);

    responses.add("process");
    responses.add(configPayment.getClientKey());

    String snapToken = SnapApi.createTransactionToken(transactionRequestBody(transactionId, grossAmount, configPayment.getServerKey()));
    responses.add(snapToken);

    getTransactionById.setStatus("process");
    transactionRepository.save(getTransactionById);

    return responses;
  }

  private Map<String, Object> inpatientRequestBody(String id, Integer grossAmount, String serverKey) {
    InPatient getInpatientById = inPatientService.getById(id);
    Animal getAnimalById = getInpatientById.getAnimal();
    Customer getCustomerById = getAnimalById.getCustomer();
    User getUserById = getCustomerById.getUserId();

    // Set serverKey to Midtrans global config
    Midtrans.serverKey = serverKey;

    // Set value to true if you want Production Environment (accept real transaction).
    Midtrans.isProduction = false;

    Map<String, Object> params = new HashMap<>();

    Map<String, String> transactionDetails = new HashMap<>();
    transactionDetails.put("order_id", id);
    transactionDetails.put("gross_amount", grossAmount.toString());

    Map<String, String> customerDetails = new HashMap<>();
    customerDetails.put("first_name", getCustomerById.getName());
    customerDetails.put("email", getUserById.getEmail());
    customerDetails.put("phone", getCustomerById.getPhone());
    customerDetails.put("shipping_address", getCustomerById.getAddress());

    Map<String, String> creditCard = new HashMap<>();
    creditCard.put("secure", "true");

    params.put("transaction_details", transactionDetails);
    params.put("customer_details", customerDetails);
    params.put("credit_card", creditCard);

    return params;
  }

  private Map<String, Object> transactionRequestBody(String id, Integer grossAmount, String serverKey) {
    Transaction getTransactionById = transactionService.getTransactionById(id);
    Customer getCustomerById = getTransactionById.getCustomer();
    User getUserById = getCustomerById.getUserId();

    // Set serverKey to Midtrans global config
    Midtrans.serverKey = serverKey;

    // Set value to true if you want Production Environment (accept real transaction).
    Midtrans.isProduction = false;

    Map<String, Object> params = new HashMap<>();

    Map<String, String> transactionDetails = new HashMap<>();
    transactionDetails.put("order_id", id);
    transactionDetails.put("gross_amount", grossAmount.toString());

    Map<String, String> customerDetails = new HashMap<>();
    customerDetails.put("first_name", getCustomerById.getName());
    customerDetails.put("email", getUserById.getEmail());
    customerDetails.put("phone", getCustomerById.getPhone());
    customerDetails.put("shipping_address", getCustomerById.getAddress());

    Map<String, String> creditCard = new HashMap<>();
    creditCard.put("secure", "true");

    params.put("transaction_details", transactionDetails);
    params.put("customer_details", customerDetails);
    params.put("credit_card", creditCard);

    return params;
  }

}
