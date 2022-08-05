package com.enigma.veterinaryclinic.specification;

import com.enigma.veterinaryclinic.dto.CageDTO;
import com.enigma.veterinaryclinic.entity.Cage;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CageSpecification {
    public static Specification<Cage> getSpecification(CageDTO cageDTO){
        return new Specification<Cage>() {
            @Override
            public Predicate toPredicate(Root<Cage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //GET ALL DATA ACTIVE
                if (cageDTO.getSearchByDelete()==false){
                    Predicate cageActivePredicate = criteriaBuilder.equal(
                            root.get("isDelete"),cageDTO.getSearchByDelete()
                    );
                    predicates.add(cageActivePredicate);
                }

                //GET ALL DATA DELETED
                if (cageDTO.getSearchByDelete()==true){
                    Predicate cageDeletedPredicate = criteriaBuilder.equal(
                            root.get("isDelete"),cageDTO.getSearchByDelete()
                    );
                    predicates.add(cageDeletedPredicate);
                }

                //SEMUA DATA
                if (cageDTO.getSearchByID() != null){
                    Predicate cageIdPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("id")),cageDTO.getSearchByID()
                    );
                    predicates.add(cageIdPredicate);
                }

                if (cageDTO.getSearchByCageName() != null){
                    Predicate cageNamePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("cageName")),"%" + cageDTO.getSearchByCageName() + "%"
                    );
                    predicates.add(cageNamePredicate);
                }

                //DATA YANG BELUM DI DELETE
                if (cageDTO.getSearchByID() != null && cageDTO.getSearchByDelete()== false){
                    Predicate cageIdPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("id")),cageDTO.getSearchByID()
                    );
                    Predicate cageActivePredicate = criteriaBuilder.equal(
                            root.get("isDelete"),cageDTO.getSearchByDelete()
                    );
                    Predicate cageIdActivePredicate = criteriaBuilder.and(cageIdPredicate,cageActivePredicate);
                    predicates.add(cageIdActivePredicate);
                }

                if (cageDTO.getSearchByCageName() != null && cageDTO.getSearchByDelete()== false){
                    Predicate cageNamePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("cageName")),"%" + cageDTO.getSearchByCageName() + "%"
                    );
                    Predicate cageActivePredicate = criteriaBuilder.equal(
                            root.get("isDelete"),cageDTO.getSearchByDelete()
                    );
                    Predicate cageNameActivePredicate = criteriaBuilder.and(cageNamePredicate,cageActivePredicate);
                    predicates.add(cageNameActivePredicate);
                }

                //DATA YANG SUDAH DI DELETE
                if (cageDTO.getSearchByID() != null && cageDTO.getSearchByDelete()== true){
                    Predicate cageIdPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("id")),cageDTO.getSearchByID()
                    );
                    Predicate cageDeletedPredicate = criteriaBuilder.equal(
                            root.get("isDelete"),cageDTO.getSearchByDelete()
                    );
                    Predicate cageIdDeletedPredicate = criteriaBuilder.and(cageIdPredicate,cageDeletedPredicate);
                    predicates.add(cageIdDeletedPredicate);
                }

                if (cageDTO.getSearchByCageName() != null && cageDTO.getSearchByDelete()== true){
                    Predicate cageNamePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("cageName")),"%" + cageDTO.getSearchByCageName() + "%"
                    );
                    Predicate cageDeletedPredicate = criteriaBuilder.equal(
                            root.get("isDelete"),cageDTO.getSearchByDelete()
                    );
                    Predicate cageNameDeletedPredicate = criteriaBuilder.and(cageNamePredicate,cageDeletedPredicate);
                    predicates.add(cageNameDeletedPredicate);
                }

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
