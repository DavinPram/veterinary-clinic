package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.entity.Role;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.RoleRepository;
import com.enigma.veterinaryclinic.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.enigma.veterinaryclinic.entity.UserRole.*;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    @Override
    public Role create(String strRole) {
        if(strRole == null){
            if(!repository.existsByRole(USER_ROLE)){
                Role userRole = new Role(USER_ROLE);
                return repository.save(userRole);
            }
            return repository.findByRole(USER_ROLE).orElseThrow(() -> new NotFoundException("Error: Role Not Found"));

        } else {
            if (strRole.equalsIgnoreCase("admin")){
                if (!repository.existsByRole(ADMIN_ROLE)){

                    Role adminRole = new Role(ADMIN_ROLE);
                    return repository.save(adminRole);
                }
                return repository.findByRole(ADMIN_ROLE).orElseThrow(()-> new NotFoundException("Error: Role Not Found"));
            }
            if (strRole.equalsIgnoreCase("doctor")){
                if (!repository.existsByRole(DOCTOR_ROLE)){

                    Role doctorRole = new Role(DOCTOR_ROLE);
                    return repository.save(doctorRole);
                }
                return repository.findByRole(DOCTOR_ROLE).orElseThrow(()-> new NotFoundException("Error: Role Not Found"));
            }
            if (strRole.equalsIgnoreCase("operator")){
                if (!repository.existsByRole(OPERATOR_ROLE)){

                    Role operatorRole = new Role(OPERATOR_ROLE);
                    return repository.save(operatorRole);
                }
                return repository.findByRole(OPERATOR_ROLE).orElseThrow(()-> new NotFoundException("Error: Role Not Found"));
            }
            else {
                if (!repository.existsByRole(USER_ROLE)){

                Role userRole = new Role(USER_ROLE);
                return repository.save(userRole);
            }
                return repository.findByRole(USER_ROLE).orElseThrow(() -> new NotFoundException("Error: Role Not Found"));
            }
        }
    }
}
