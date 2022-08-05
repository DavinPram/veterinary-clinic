package com.enigma.veterinaryclinic.specification;

import com.enigma.veterinaryclinic.dto.OperatorDTO;
import com.enigma.veterinaryclinic.entity.Operator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class OperatorSpecification {
    public static Specification<Operator> getSpecification(OperatorDTO operatorDTO){
        return new Specification<Operator>() {
            @Override
            public Predicate toPredicate(Root<Operator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //GET ALL DATA ACTIVE
                if (operatorDTO.getSearchByDelete()==false){
                    Predicate operatorActivePredicate = criteriaBuilder.equal(
                            root.get("isDeleted"),operatorDTO.getSearchByDelete()
                    );
                    predicates.add(operatorActivePredicate);
                }

                //GET ALL DATA DELETED
                if (operatorDTO.getSearchByDelete()==true){
                    Predicate operatorDeletedPredicate = criteriaBuilder.equal(
                            root.get("isDeleted"),operatorDTO.getSearchByDelete()
                    );
                    predicates.add(operatorDeletedPredicate);
                }

                //ALL DATA
                if (operatorDTO.getSearchById() != null){
                    Predicate operatorIdPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("id")),operatorDTO.getSearchById()
                    );
                    predicates.add(operatorIdPredicate);
                }

                if (operatorDTO.getSearchByName() != null){
                    Predicate operatorNamePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),"%"+operatorDTO.getSearchByName()+"%"
                    );
                    predicates.add(operatorNamePredicate);
                }

                //DATA ACTIVE
                if (operatorDTO.getSearchById() != null){
                    Predicate operatorIdPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("id")),operatorDTO.getSearchById()
                    );
                    Predicate operatorActivePredicate = criteriaBuilder.equal(
                            root.get("isDeleted"),operatorDTO.getSearchByDelete()
                    );
                    Predicate operatorNameActivePredicate = criteriaBuilder.and(operatorIdPredicate,operatorActivePredicate);
                    predicates.add(operatorNameActivePredicate);
                }

                if (operatorDTO.getSearchByName() != null && operatorDTO.getSearchByDelete()==false){
                    Predicate operatorNamePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),"%"+operatorDTO.getSearchByName()+"%"
                    );
                    Predicate operatorActivePredicate = criteriaBuilder.equal(
                            root.get("isDeleted"),operatorDTO.getSearchByDelete()
                    );
                    Predicate operatorNameActivePredicate = criteriaBuilder.and(operatorNamePredicate,operatorActivePredicate);
                    predicates.add(operatorNameActivePredicate);
                }

                //DATA DELETED
                if (operatorDTO.getSearchById() != null){
                    Predicate operatorIdPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("id")),operatorDTO.getSearchById()
                    );
                    Predicate operatorDeletedPredicate = criteriaBuilder.equal(
                            root.get("isDeleted"),operatorDTO.getSearchByDelete()
                    );
                    Predicate operatorIdDeletedPredicate = criteriaBuilder.and(operatorIdPredicate,operatorDeletedPredicate);
                    predicates.add(operatorIdDeletedPredicate);
                }

                if (operatorDTO.getSearchByName() != null && operatorDTO.getSearchByDelete()==true){
                    Predicate operatorNamePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),"%"+operatorDTO.getSearchByName()+"%"
                    );
                    Predicate operatorDeletedPredicate = criteriaBuilder.equal(
                            root.get("isDeleted"),operatorDTO.getSearchByDelete()
                    );
                    Predicate operatorNameDeletedPredicate = criteriaBuilder.and(operatorNamePredicate,operatorDeletedPredicate);
                    predicates.add(operatorNameDeletedPredicate);
                }

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
