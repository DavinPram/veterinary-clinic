package com.enigma.veterinaryclinic.specification;

import com.enigma.veterinaryclinic.dto.CustomerDTO;
import com.enigma.veterinaryclinic.entity.Customer;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CustomerSpecification {
    public static Specification<Customer> getSpecification(CustomerDTO customerDTO) {
        return  new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (customerDTO.getSearchByCustomerName() != null){
                    Predicate customerNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + customerDTO.getSearchByCustomerName().toLowerCase() + "%");
                    predicates.add(customerNamePredicate);
                }
                if (customerDTO.getSearchByUserId()!= null){
                    Predicate userIdPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("userId")), "%" + customerDTO.getSearchByUserId().toLowerCase() + "%");
                    predicates.add(userIdPredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
