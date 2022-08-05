package com.enigma.veterinaryclinic.repository;

import com.enigma.veterinaryclinic.entity.Type;
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
public interface TypeRepository extends JpaRepository<Type, String> {
  Page<Type> findAll(Specification<Type> specification, Pageable pageable);

  @Query(value = "SELECT c FROM Type c WHERE c.isDeleted = false AND c.id = ?1")
  Optional<Type> getActiveType(String id);
}
