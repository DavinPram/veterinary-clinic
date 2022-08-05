package com.enigma.veterinaryclinic.repository;


import com.enigma.veterinaryclinic.entity.Role;
import com.enigma.veterinaryclinic.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRole (UserRole role);
    Boolean existsByRole(UserRole role);
}
