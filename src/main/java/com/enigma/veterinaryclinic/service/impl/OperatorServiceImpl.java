package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.dto.OperatorDTO;
import com.enigma.veterinaryclinic.entity.Operator;
import com.enigma.veterinaryclinic.exception.BadRequestException;
import com.enigma.veterinaryclinic.exception.MethodNotAllowedException;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.OperatorRepository;
import com.enigma.veterinaryclinic.service.OperatorService;
import com.enigma.veterinaryclinic.specification.OperatorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    public Operator create(Operator operator) {
        if (operator.getName()==null || operator.getName().equalsIgnoreCase("") || operator.getName().isEmpty()) throw new BadRequestException("The Sent JSON Has Flaws");
        else return operatorRepository.save(operator);
    }

    @Override
    public Operator getById(String id) {
        Operator operator = findByIdOrThrowNotFound(id);
        if (operator.getIsDeleted()==true) throw new NotFoundException(String.format("Operator With Id %s Not Found",id));
        else return operator;
    }

    @Override
    public Page<Operator> getData(Pageable pageable, OperatorDTO operatorDTO) {
        Specification<Operator> specification = OperatorSpecification.getSpecification(operatorDTO);
        return operatorRepository.findAll(specification, pageable);
    }

    @Override
    public Page<Operator> getAll(Pageable pageable) {
        return operatorRepository.findAll(pageable);
    }

    @Override
    public Operator update(Operator operator) {
        findByIdOrThrowNotFound(operator.getId());
        if (operator.getIsDeleted()==null) operator.setIsDeleted(false);
        return operatorRepository.save(operator);
    }

    @Override
    public String delete(String id) {
        if (id.isEmpty() || id==null || id.equalsIgnoreCase("")) throw new MethodNotAllowedException("Need Id!");
        Operator operator = findByIdOrThrowNotFound(id);
        if (operator.getIsDeleted()==true) throw new NotFoundException(String.format("Operator With Id %s Not Found", operator.getId()));
        operator.setIsDeleted(true);
        operator.setDeletedAt(new Date());
        operatorRepository.save(operator);
        return String.format("Operator With Id %s is Deleted",id);
    }

    private Operator findByIdOrThrowNotFound(String id){
        Optional<Operator> operator = operatorRepository.findById(id);
        if (operator.isPresent()) return operator.get();
        else throw new NotFoundException(String.format("Operator With Id %s Not Found", id));
    }
}
