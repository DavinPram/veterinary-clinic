package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.dto.CustomerDTO;
import com.enigma.veterinaryclinic.entity.Customer;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.service.CustomerService;
import com.enigma.veterinaryclinic.util.WebResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/customers"})
@SecurityRequirement(name = "vetclinicapi")
@NoArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<?>> createCustomer (@RequestBody Customer request){
        Customer customer = customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body( new WebResponse<>("Customer successfully created",customer));
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<?>>> getAllCustomer(
        @RequestParam(name = "size", defaultValue = "10") Integer size,
        @RequestParam(name = "page",defaultValue = "0") Integer page,
        @RequestParam(name = "sortBy",defaultValue = "name") String sortBy,
        @RequestParam(name = "direction",defaultValue = "ASC") String direction,
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "userId",required = false) String userId
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        CustomerDTO customerDTO = new CustomerDTO(name,userId);
        Page<Customer> customers =customerService.getAll(pageable,customerDTO,sort);
        PageResponse<Customer> pageResponse =new PageResponse<>(
            customers.getContent(),
            customers.getTotalElements(),
            customers.getTotalPages(),
            page,
            size,
            sortBy
        );
        return ResponseEntity.status(HttpStatus.OK).body( new WebResponse<>("All Customer Data",pageResponse));
    }

    @GetMapping(value = "/{customerId}")
    public ResponseEntity<WebResponse<?>> getCustomerById(@PathVariable("customerId") String id )
            throws NotFoundException{
        Customer customer = customerService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).
                body(new WebResponse<>(String.format("Customer with ID %s found",id),customer));
    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity<WebResponse<?>> updateCustomerById(
            @PathVariable("customerId") String id,
            @RequestBody Customer customer ) throws NotFoundException {
        Customer update = customerService.update(id,customer);
        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Successfully Updated Customer Data with ID %s",id),update));
    }

    @DeleteMapping(value = "/{customerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<String>> deleteCustomerById(@PathVariable("customerId") String id){
        String deleteCustomer = customerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("ID %s successfully deleted",id),deleteCustomer));
    }


}
