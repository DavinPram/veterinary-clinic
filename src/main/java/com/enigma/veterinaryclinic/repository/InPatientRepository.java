package com.enigma.veterinaryclinic.repository;

import com.enigma.veterinaryclinic.entity.InPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InPatientRepository extends JpaRepository<InPatient, String> {
}
