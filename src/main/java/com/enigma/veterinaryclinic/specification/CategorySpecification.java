package com.enigma.veterinaryclinic.specification;

import com.enigma.veterinaryclinic.dto.CategoryDTO;
import com.enigma.veterinaryclinic.entity.Category;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CategorySpecification {

    public static Specification<Category> getSpecification(CategoryDTO categoryDTO) {
        return new Specification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if (categoryDTO.getSearchByCategoryName() != null) {
                    Predicate categoryPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                            "%" + categoryDTO.getSearchByCategoryName().toLowerCase() + "%");
                    predicates.add(categoryPredicate);

                }
                Predicate[] arrayPredicate = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicate);
            }
        };
    }
}
