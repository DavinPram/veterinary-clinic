package com.enigma.veterinaryclinic.repository;

import com.enigma.veterinaryclinic.entity.Cage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CageRepository extends JpaRepository<Cage, String> {

//    @Query(value = "SELECT e FROM Cage e WHERE e.deleteAt IS NULL")
//    Page<Cage> findAllActiveSearch(Specification<Cage> specification, Pageable pageable);

//    @Query(value = "SELECT e FROM Cage e WHERE e.deleteAt IS NOT NULL")
//    Page<Cage> findAllDeletedSearch(Specification<Cage> specification, Pageable pageable);

    Page<Cage> findAll(Specification<Cage> specification, Pageable pageable);

//    @Query(value = "SELECT e FROM Cage e WHERE e.deleteAt IS NULL")
//    Page<Cage> findAllActive(Pageable pageable);

//    @Query(value = "SELECT e FROM Cage e WHERE e.deleteAt IS NOT NULL")
//    Page<Cage> findAllDeleted(Pageable pageable);
}
