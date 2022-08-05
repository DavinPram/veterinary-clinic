package com.enigma.veterinaryclinic.specification;

import com.enigma.veterinaryclinic.dto.QueueDTO;
import com.enigma.veterinaryclinic.entity.Transaction;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class QueueSpecification {
    public static Specification<Transaction> getSpecification(QueueDTO queueDTO) {
        return new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (queueDTO.getSearchDate() != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String dateInString = formatter.format(Date.valueOf(queueDTO.getSearchDate()));
                    predicates.add(criteriaBuilder.equal(criteriaBuilder.function("TO_CHAR",
                            String.class, root.get("date"),criteriaBuilder.literal("yyyy-MM-dd")), dateInString));
                }
                Predicate[] arrayPredicate = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicate);
            }
        };
    }
}
