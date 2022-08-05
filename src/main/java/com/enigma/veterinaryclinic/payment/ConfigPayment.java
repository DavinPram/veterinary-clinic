package com.enigma.veterinaryclinic.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConfigPayment {
  private Boolean isProduction = false;

  private String clientKey = "SB-Mid-client-3bFUrssQLN01DDqL";

  private String serverKey = "SB-Mid-server-x5lq31FWCGoPreWqj3fGEr8B";
}
