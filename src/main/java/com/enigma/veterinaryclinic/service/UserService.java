package com.enigma.veterinaryclinic.service;

import com.enigma.veterinaryclinic.entity.*;
import com.enigma.veterinaryclinic.response.RegisterResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {


    RegisterResponse createCustomer (User user, Customer customer, Set<Role> roles);

    RegisterResponse createOperator(User user, Operator operator, Set<Role> roles);

    RegisterResponse createDoctor(User user, Doctor doctor, Set<Role> roles);
}
