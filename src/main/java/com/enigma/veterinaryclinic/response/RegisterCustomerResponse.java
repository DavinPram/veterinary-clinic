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
public class RegisterCustomerResponse {
    private String id;

    private String username;

    private String name;

    private String address;

    private String phone;

    private Date createdAt;

    private Date updatedAt;

    private Set<String> roles;



}
