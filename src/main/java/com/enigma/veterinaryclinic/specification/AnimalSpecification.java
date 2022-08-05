package com.enigma.veterinaryclinic.specification;

import com.enigma.veterinaryclinic.dto.AnimalDTO;
import com.enigma.veterinaryclinic.entity.Animal;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AnimalSpecification {
    public static Specification<Animal> getSpecification(AnimalDTO animalDTO){
        return new Specification<Animal>() {
            @Override
            public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //GET ALL DATA ACTIVE
                if (animalDTO.getSearchByDelete()==false){
                    Predicate animalActivePredicate = criteriaBuilder.equal(
                            root.get("isDeleted"),animalDTO.getSearchByDelete()
                    );
                    predicates.add(animalActivePredicate);
                }

                //GET ALL DATA DELETED
                if (animalDTO.getSearchByDelete()==true){
                    Predicate animalDeletedPredicate = criteriaBuilder.equal(
                            root.get("isDeleted"),animalDTO.getSearchByDelete()
                    );
                    predicates.add(animalDeletedPredicate);
                }

                //SEMUA DATA
                if (animalDTO.getSearchById() != null){
                    Predicate animalIdPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("id")),animalDTO.getSearchById()
                    );
                    predicates.add(animalIdPredicate);
                }

                if (animalDTO.getSearchByName() != null){
                    Predicate animalNamePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),"%"+animalDTO.getSearchByName()+"%"
                    );
                    predicates.add(animalNamePredicate);
                }

                if (animalDTO.getSearchByMinAge() != null){
                    Predicate animalMinAgePredicate = criteriaBuilder.greaterThanOrEqualTo(
                            root.get("age"),animalDTO.getSearchByMinAge()
                    );
                    predicates.add(animalMinAgePredicate);
                }

                if (animalDTO.getSearchByMaxAge() != null){
                    Predicate animalMaxAgePredicate = criteriaBuilder.lessThanOrEqualTo(
                            root.get("age"),animalDTO.getSearchByMaxAge()
                    );
                    predicates.add(animalMaxAgePredicate);
                }

//                //DATA YANG BELUM DI DELETE
//                if (animalDTO.getSearchById() != null && animalDTO.getSearchByDelete()==false){
//                    Predicate animalIdPredicate = criteriaBuilder.like(
//                            criteriaBuilder.lower(root.get("id")),animalDTO.getSearchById()
//                    );
//                    Predicate animalActivePredicate = criteriaBuilder.equal(
//                            root.get("isDeleted"),animalDTO.getSearchByDelete()
//                    );
//                    Predicate animalIdActivePredicate = criteriaBuilder.and(animalIdPredicate,animalActivePredicate);
//                    predicates.add(animalIdActivePredicate);
//                }
//
//                if (animalDTO.getSearchByName() != null && animalDTO.getSearchByDelete()==false){
//                    Predicate animalNamePredicate = criteriaBuilder.like(
//                            criteriaBuilder.lower(root.get("name")),"%"+animalDTO.getSearchByName()+"%"
//                    );
//                    Predicate animalActivePredicate = criteriaBuilder.equal(
//                            root.get("isDeleted"),animalDTO.getSearchByDelete()
//                    );
//                    Predicate animalNameActivePredicate = criteriaBuilder.and(animalNamePredicate,animalActivePredicate);
//                    predicates.add(animalNameActivePredicate);
//                }
//
//                if (animalDTO.getSearchByMinAge() != null && animalDTO.getSearchByDelete()==false){
//                    Predicate animalMinAgePredicate = criteriaBuilder.greaterThanOrEqualTo(
//                            root.get("age"),animalDTO.getSearchByMinAge()
//                    );
//                    Predicate animalActivePredicate = criteriaBuilder.equal(
//                            root.get("isDeleted"),animalDTO.getSearchByDelete()
//                    );
//                    Predicate animalMinAgeActivePredicate = criteriaBuilder.and(animalMinAgePredicate,animalActivePredicate);
//                    predicates.add(animalMinAgeActivePredicate);
//                }
//
//                if (animalDTO.getSearchByMaxAge() != null && animalDTO.getSearchByDelete()==false){
//                    Predicate animalMaxAgePredicate = criteriaBuilder.lessThanOrEqualTo(
//                            root.get("age"),animalDTO.getSearchByMaxAge()
//                    );
//                    Predicate animalActivePredicate = criteriaBuilder.equal(
//                            root.get("isDeleted"),animalDTO.getSearchByDelete()
//                    );
//                    Predicate animalMaxAgeActivePredicate = criteriaBuilder.and(animalMaxAgePredicate,animalActivePredicate);
//                    predicates.add(animalMaxAgeActivePredicate);
//                }

//                //DATA YANG SUDAH DI DELETE
//                if (animalDTO.getSearchById() != null && animalDTO.getSearchByDelete()==true){
//                    Predicate animalIdPredicate = criteriaBuilder.like(
//                            criteriaBuilder.lower(root.get("id")),animalDTO.getSearchById()
//                    );
//                    Predicate animalDeletePredicate = criteriaBuilder.equal(
//                            root.get("isDeleted"),animalDTO.getSearchByDelete()
//                    );
//                    Predicate animalIdDeletePredicate = criteriaBuilder.and(animalIdPredicate,animalDeletePredicate);
//                    predicates.add(animalIdDeletePredicate);
//                }
//
//                if (animalDTO.getSearchByName() != null && animalDTO.getSearchByDelete()==true){
//                    Predicate animalNamePredicate = criteriaBuilder.like(
//                            criteriaBuilder.lower(root.get("name")),"%"+animalDTO.getSearchByName()+"%"
//                    );
//                    Predicate animalDeletePredicate = criteriaBuilder.equal(
//                            root.get("isDeleted"),animalDTO.getSearchByDelete()
//                    );
//                    Predicate animalNameDeletePredicate = criteriaBuilder.and(animalNamePredicate,animalDeletePredicate);
//                    predicates.add(animalNameDeletePredicate);
//                }
//
//                if (animalDTO.getSearchByMinAge() != null && animalDTO.getSearchByDelete()==true){
//                    Predicate animalMinAgePredicate = criteriaBuilder.greaterThanOrEqualTo(
//                            root.get("age"),animalDTO.getSearchByMinAge()
//                    );
//                    Predicate animalDeletePredicate = criteriaBuilder.equal(
//                            root.get("isDeleted"),animalDTO.getSearchByDelete()
//                    );
//                    Predicate animalMinAgeDeletePredicate = criteriaBuilder.and(animalMinAgePredicate,animalDeletePredicate);
//                    predicates.add(animalMinAgeDeletePredicate);
//                }
//
//                if (animalDTO.getSearchByMaxAge() != null && animalDTO.getSearchByDelete()==true){
//                    Predicate animalMaxAgePredicate = criteriaBuilder.lessThanOrEqualTo(
//                            root.get("age"),animalDTO.getSearchByMaxAge()
//                    );
//                    Predicate animalDeletePredicate = criteriaBuilder.equal(
//                            root.get("isDeleted"),animalDTO.getSearchByDelete()
//                    );
//                    Predicate animalMaxAgeDeletePredicate = criteriaBuilder.and(animalMaxAgePredicate,animalDeletePredicate);
//                    predicates.add(animalMaxAgeDeletePredicate);
//                }

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
