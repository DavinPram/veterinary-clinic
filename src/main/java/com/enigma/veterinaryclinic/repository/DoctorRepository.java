package com.enigma.veterinaryclinic.repository;

import com.enigma.veterinaryclinic.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface DoctorRepository extends JpaRepository<Doctor, String> {
  @Query(value = "SELECT c FROM Doctor c WHERE c.isDeleted = false AND c.id = ?1")
  Optional<Doctor> getActive(String id);

  Page<Doctor> findAll(Specification<Doctor> specification, Pageable pageable);
}
