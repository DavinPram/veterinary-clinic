package com.enigma.veterinaryclinic.specification;

import com.enigma.veterinaryclinic.dto.DoctorDTO;
import com.enigma.veterinaryclinic.entity.Doctor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class DoctorSpecification {
  public DoctorSpecification() {
  }

  public static Specification<Doctor> getSpecification(DoctorDTO doctorDTO) {
    return new Specification<Doctor>() {
      @Override
      public Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(doctorDTO.getSearchByName() != null) {
          predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + doctorDTO.getSearchByName().toLowerCase() + "%"));
        }

        if(doctorDTO.getSearchByAlumni() != null) {
          predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("alumni")), "%" + doctorDTO.getSearchByAlumni().toLowerCase() + "%"));
        }

        if(doctorDTO.getSearchByExperience() != null) {
          predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("experience")), "%" + doctorDTO.getSearchByExperience().toLowerCase() + "%"));
        }

        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));

        Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
        return criteriaBuilder.and(arrayPredicates);
      }
    };
  }
}
