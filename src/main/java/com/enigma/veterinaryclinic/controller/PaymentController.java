package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.entity.InPatient;
import com.enigma.veterinaryclinic.entity.Transaction;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.payment.ConfigPayment;
import com.enigma.veterinaryclinic.repository.InPatientRepository;
import com.enigma.veterinaryclinic.repository.TransactionRepository;
import com.enigma.veterinaryclinic.request.PaymentRequest;
import com.enigma.veterinaryclinic.request.StatusPaymentRequest;
import com.enigma.veterinaryclinic.response.RegisterPaymentResponse;
import com.enigma.veterinaryclinic.response.StatusPaymentResponse;
import com.enigma.veterinaryclinic.response.WebResponse;
import com.enigma.veterinaryclinic.service.InPatientService;
import com.enigma.veterinaryclinic.service.PaymentService;
import com.enigma.veterinaryclinic.service.TransactionService;
import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransCoreApi;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/payment")
public class PaymentController {

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private InPatientService inPatientService;

  @Autowired
  private InPatientRepository inPatientRepository;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private TransactionRepository transactionRepository;

  @PostMapping("/inpatient/checkout")
  public ResponseEntity<WebResponse<RegisterPaymentResponse>> inPatientcheckout(@RequestBody PaymentRequest request) throws MidtransError {

    List<String> responsePayment = paymentService.paymentInpatient(request.getId(), request.getGrossAmount());
    RegisterPaymentResponse paymentResponse = new RegisterPaymentResponse();

    paymentResponse.setStatus(responsePayment.get(0));
    paymentResponse.setClientKey(responsePayment.get(1));
    paymentResponse.setSnapKey(responsePayment.get(2));

    WebResponse<RegisterPaymentResponse> response = new WebResponse<>("Success", paymentResponse);
    return ResponseEntity.status(HttpStatus.OK)
            .body(response);
  }

  @PostMapping("/inpatient/status")
  public ResponseEntity<WebResponse<StatusPaymentResponse>> inpatientStatus(@RequestBody StatusPaymentRequest transactionId) throws MidtransError {
    ConfigPayment configPayment = new ConfigPayment();
    Config config = Config
						.builder()
						.enableLog(true)
						.setIsProduction(configPayment.getIsProduction())
						.setClientKey(configPayment.getClientKey())
						.setServerKey(configPayment.getServerKey())
						.build();

    MidtransCoreApi coreApi = new ConfigFactory(config).getCoreApi();
    JSONObject result = coreApi.checkTransaction(transactionId.getTransactionId());

    StatusPaymentResponse statusPaymentResponse = new StatusPaymentResponse();
    statusPaymentResponse.setStatus(result.getString("transaction_status"));
    statusPaymentResponse.setGrossAmount(result.getString("gross_amount"));

    JSONArray arrayVirtualAccount = result.getJSONArray("va_numbers");
    JSONObject objectVirtualAccount = arrayVirtualAccount.getJSONObject(0);
    statusPaymentResponse.setBank(objectVirtualAccount.getString("bank"));
    statusPaymentResponse.setAccountNumber(objectVirtualAccount.getString("va_number"));

    String transactionTime = result.getString("transaction_time");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s", Locale.ENGLISH);
    LocalDateTime changeTransactionTime = LocalDateTime.parse(transactionTime, formatter);
    LocalDateTime expired = changeTransactionTime.plusDays(1);
    statusPaymentResponse.setExpired(expired);

    WebResponse<StatusPaymentResponse> response = new WebResponse<>("Success", statusPaymentResponse);

    InPatient byId = inPatientService.getById(transactionId.getTransactionId());
    byId.setStatus(result.getString("transaction_status"));

    if (result.getString("transaction_status").equalsIgnoreCase("settlement")) {
      byId.setPayment(true);

      String settTime = result.getString("settlement_time");
      DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s", Locale.ENGLISH);
      LocalDateTime settTimeSave = LocalDateTime.parse(transactionTime, formatter);
      Date settDate = Date.from(settTimeSave.atZone(ZoneId.systemDefault()).toInstant());
      byId.setCheckoutAt(settDate);
    }
    inPatientRepository.save(byId);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/transaction/checkout")
  public ResponseEntity<WebResponse<RegisterPaymentResponse>> transactionCheckout(@RequestBody PaymentRequest request) throws MidtransError {

    List<String> responsePayment = paymentService.paymentTransaction(request.getId(), request.getGrossAmount());
    RegisterPaymentResponse paymentResponse = new RegisterPaymentResponse();

    paymentResponse.setStatus(responsePayment.get(0));
    paymentResponse.setClientKey(responsePayment.get(1));
    paymentResponse.setSnapKey(responsePayment.get(2));

    WebResponse<RegisterPaymentResponse> response = new WebResponse<>("Success", paymentResponse);
    return ResponseEntity.status(HttpStatus.OK)
            .body(response);
  }

  @PostMapping("/transaction/status")
  public ResponseEntity<WebResponse<StatusPaymentResponse>> transactionStatus(@RequestBody StatusPaymentRequest transactionId) throws MidtransError {
    ConfigPayment configPayment = new ConfigPayment();
    Config config = Config
            .builder()
            .enableLog(true)
            .setIsProduction(configPayment.getIsProduction())
            .setClientKey(configPayment.getClientKey())
            .setServerKey(configPayment.getServerKey())
            .build();

    MidtransCoreApi coreApi = new ConfigFactory(config).getCoreApi();
    JSONObject result = coreApi.checkTransaction(transactionId.getTransactionId());

    StatusPaymentResponse statusPaymentResponse = new StatusPaymentResponse();
    statusPaymentResponse.setStatus(result.getString("transaction_status"));
    statusPaymentResponse.setGrossAmount(result.getString("gross_amount"));

    JSONArray arrayVirtualAccount = result.getJSONArray("va_numbers");
    JSONObject objectVirtualAccount = arrayVirtualAccount.getJSONObject(0);
    statusPaymentResponse.setBank(objectVirtualAccount.getString("bank"));
    statusPaymentResponse.setAccountNumber(objectVirtualAccount.getString("va_number"));

    String transactionTime = result.getString("transaction_time");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s", Locale.ENGLISH);
    LocalDateTime changeTransactionTime = LocalDateTime.parse(transactionTime, formatter);
    LocalDateTime expired = changeTransactionTime.plusDays(1);
    statusPaymentResponse.setExpired(expired);

    WebResponse<StatusPaymentResponse> response = new WebResponse<>("Success", statusPaymentResponse);

    Transaction getTransactionById = transactionService.getTransactionById(transactionId.getTransactionId());
    getTransactionById.setStatus(result.getString("transaction_status"));

    if (result.getString("transaction_status").equalsIgnoreCase("settlement")) {
      getTransactionById.setPayment(true);

      String settTime = result.getString("settlement_time");
      DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s", Locale.ENGLISH);
      LocalDateTime settTimeSave = LocalDateTime.parse(transactionTime, formatter);
      Date settDate = Date.from(settTimeSave.atZone(ZoneId.systemDefault()).toInstant());
      getTransactionById.setCheckoutAt(settDate);
    }
    transactionRepository.save(getTransactionById);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }


}
