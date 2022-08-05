package com.enigma.veterinaryclinic.specification;

import com.enigma.veterinaryclinic.dto.TypeDTO;
import com.enigma.veterinaryclinic.entity.Type;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TypeSpecification {
  public TypeSpecification() {
  }

  public static Specification<Type> getSpecification(TypeDTO typeDTO) {
    return new Specification<Type>() {
      @Override
      public Predicate toPredicate(Root<Type> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (typeDTO.getSearchByType() != null) {
//          Predicate typeNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%" + typeDTO.getSearchByType().toLowerCase() + "%");
//          predicates.add(typeNamePredicate);
          predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%" + typeDTO.getSearchByType().toLowerCase() + "%"));
        }
        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));

        Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
        return criteriaBuilder.and(arrayPredicates);
      }
    };
  }
}
