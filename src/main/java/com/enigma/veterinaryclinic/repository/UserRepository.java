package com.enigma.veterinaryclinic.repository;

import com.enigma.veterinaryclinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
        Optional<User> findByUsername(String username);
}
