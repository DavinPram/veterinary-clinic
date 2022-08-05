package com.enigma.veterinaryclinic.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterResponse {

  private String userId;

  private String username;

  private String email;

  private String id;

  private String name;

  private Date createdAt;

  private Date updatedAt;

  private Set<String> roles;

}
