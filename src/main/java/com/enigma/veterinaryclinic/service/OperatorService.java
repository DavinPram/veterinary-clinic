package com.enigma.veterinaryclinic.service;

import com.enigma.veterinaryclinic.dto.OperatorDTO;
import com.enigma.veterinaryclinic.entity.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OperatorService {
    Operator create(Operator operator);
    Operator getById(String id);
    Page<Operator> getData(Pageable pageable, OperatorDTO operatorDTO);
    Page<Operator> getAll(Pageable pageable);
    Operator update(Operator operator);
    String delete(String id);
}
