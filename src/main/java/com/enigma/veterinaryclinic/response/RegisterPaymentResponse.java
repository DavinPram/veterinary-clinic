package com.enigma.veterinaryclinic.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterPaymentResponse {
  private String status;

  private String clientKey;

  private String snapKey;
}
