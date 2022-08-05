package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.entity.*;
import com.enigma.veterinaryclinic.repository.CustomerRepository;
import com.enigma.veterinaryclinic.repository.DoctorRepository;
import com.enigma.veterinaryclinic.repository.OperatorRepository;
import com.enigma.veterinaryclinic.repository.UserRepository;
import com.enigma.veterinaryclinic.response.RegisterResponse;
import com.enigma.veterinaryclinic.service.RoleService;
import com.enigma.veterinaryclinic.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private DoctorRepository doctorRepository;


    @Override
    public RegisterResponse createCustomer (User user, Customer customer, Set<Role> roles) {

        user.setRoles(roles);
        User saveUser = userRepository.save(user);
        customer.setUserId(saveUser);
        Customer saveCustomer = customerRepository.save(customer);

        Set<String> strRoles = new HashSet<>();
        for (Role role:saveUser.getRoles()) {
            strRoles.add(role.getRole().name());
        }
        return new RegisterResponse(
                saveUser.getId(),
                saveUser.getUsername(),
                saveUser.getEmail(),
                saveCustomer.getId(),
                saveCustomer.getName(),
                saveUser.getCreatedAt(),
                saveUser.getUpdatedAt(),
                strRoles
        );
    }

    @Override
    public RegisterResponse createOperator(User user, Operator operator, Set<Role> roles) {
        user.setRoles(roles);
        User saveUser = userRepository.save(user);
        operator.setUserId(saveUser);
        Operator saveOperator = operatorRepository.save(operator);

        Set<String> strRoles = new HashSet<>();
        for (Role role:saveUser.getRoles()) {
            strRoles.add(role.getRole().name());
        }

        return new RegisterResponse(
                saveUser.getId(),
                saveUser.getUsername(),
                saveUser.getEmail(),
                saveOperator.getId(),
                saveOperator.getName(),
                saveUser.getCreatedAt(),
                saveUser.getUpdatedAt(),
                strRoles
        );
    }

    @Override
    public RegisterResponse createDoctor(User user, Doctor doctor, Set<Role> roles) {
        user.setRoles(roles);
        User saveUser = userRepository.save(user);
        doctor.setUserId(saveUser);
        Doctor saveDoctor = doctorRepository.save(doctor);

        Set<String> strRoles = new HashSet<>();
        for (Role role:saveUser.getRoles()) {
            strRoles.add(role.getRole().name());
        }

        return new RegisterResponse(
                saveUser.getId(),
                saveUser.getUsername(),
                saveUser.getEmail(),
                saveDoctor.getId(),
                saveDoctor.getName(),
                saveUser.getCreatedAt(),
                saveUser.getUpdatedAt(),
                strRoles
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username Not Registered"));
        return UserDetailImpl.build(user);
    }


}
