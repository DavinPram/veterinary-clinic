package com.enigma.veterinaryclinic.service;

import com.midtrans.httpclient.error.MidtransError;

import java.util.List;

public interface PaymentService {
  List<String> paymentInpatient(String inpatientId, Integer grossAmount) throws MidtransError;
  List<String> paymentTransaction(String transactionId, Integer grossAmount) throws MidtransError;
}
