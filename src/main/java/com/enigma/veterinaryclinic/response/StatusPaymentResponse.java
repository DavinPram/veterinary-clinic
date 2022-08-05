package com.enigma.veterinaryclinic.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatusPaymentResponse {
  private String status;

  private String grossAmount;

  private String bank;

  private String accountNumber;

  private LocalDateTime expired;

}
