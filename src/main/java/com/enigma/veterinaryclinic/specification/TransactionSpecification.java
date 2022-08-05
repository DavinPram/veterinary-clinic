package com.enigma.veterinaryclinic.specification;

import com.enigma.veterinaryclinic.dto.TransactionDTO;
import com.enigma.veterinaryclinic.entity.Transaction;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class TransactionSpecification {
    public static Specification<Transaction> getSpecification(TransactionDTO transactionDTO){
        return new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();

                if(transactionDTO.getPaymentStatus()!=null){
                    Predicate paymentStatusPredicate = criteriaBuilder.equal(root.get("payment"),
                            transactionDTO.getPaymentStatus());
                    predicates.add(paymentStatusPredicate);
                }

                Predicate[] arrayPredicate = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicate);
            }
        };
    }
}
