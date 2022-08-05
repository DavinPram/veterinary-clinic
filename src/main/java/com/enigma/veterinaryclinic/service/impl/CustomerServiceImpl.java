package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.dto.CustomerDTO;
import com.enigma.veterinaryclinic.entity.Customer;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.CustomerRepository;
import com.enigma.veterinaryclinic.service.CustomerService;
import com.enigma.veterinaryclinic.specification.CustomerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    //Create new Customer
    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer) ;
    }

    //Get All data customer
    @Override
    public Page<Customer> getAll(Pageable pageable, CustomerDTO customerDTO, Sort sort) {
        Specification<Customer> specification = CustomerSpecification.getSpecification(customerDTO);
        return customerRepository.findAll(specification,pageable);
    }

    @Override
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }


    @Override
    public Customer update(String id, Customer customer) {
        Customer updateCustomer = findByIdOrThrowNotFound(id);
        updateCustomer.setName(customer.getName());
        updateCustomer.setAddress(customer.getAddress());
        updateCustomer.setPhone(customer.getPhone());

        return customerRepository.save(updateCustomer);
    }

    @Override
    public String delete(String id) {
        Customer customer = findByIdOrThrowNotFound(id);
        if (customer.getIsDeleted()) {
            throw new NotFoundException("Customer not found");
        }else {
            customer.setIsDeleted(true);
            customerRepository.save(customer);
        }
        return "Customer has been deleted";
    }
    private Customer findByIdOrThrowNotFound(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new NotFoundException("Customer tidak ditemukan");
        }
    }


}
