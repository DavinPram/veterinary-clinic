package com.enigma.veterinaryclinic.specification;

import com.enigma.veterinaryclinic.dto.ProductDTO;
import com.enigma.veterinaryclinic.entity.Product;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ProductSpecification {
    public static Specification<Product> getSpecification(ProductDTO productDTO) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if (productDTO.getSearchByName() != null) {
                    Predicate productSearchByNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                            "%" + productDTO.getSearchByName().toLowerCase() + "%");
                    predicates.add(productSearchByNamePredicate);
                }
                if(productDTO.getRangeMinPrice()!= null){
                    Predicate productRangeMinPrice = criteriaBuilder.greaterThanOrEqualTo(root.get("price"),
                            productDTO.getRangeMinPrice());
                    predicates.add(productRangeMinPrice);

                }
                if(productDTO.getRangeMaxPrice()!= null){
                    Predicate productRangeMaxPrice = criteriaBuilder.lessThanOrEqualTo(root.get("price"),
                            productDTO.getRangeMaxPrice());
                    predicates.add(productRangeMaxPrice);

                }

                Predicate[] arrayPredicate = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicate);
            }
        };
    }
}
