package com.enigma.veterinaryclinic.service;

import com.enigma.veterinaryclinic.dto.CustomerDTO;
import com.enigma.veterinaryclinic.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface CustomerService {
    Customer create (Customer customer);
    Page<Customer> getAll (Pageable pageable, CustomerDTO customerDTO, Sort sort);
    Customer getById(String id);
    Customer update ( String id,Customer customer);
    String delete (String id);
}
