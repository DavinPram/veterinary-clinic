package com.enigma.veterinaryclinic.service;

import com.enigma.veterinaryclinic.dto.CageDTO;
import com.enigma.veterinaryclinic.entity.Cage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CageService {
    Cage create(Cage cage);
    Cage getById(String id);
    Page<Cage> getData(Pageable pageable, CageDTO cageDTO);
    Page<Cage> getAll(Pageable pageable);
    Cage update(Cage cage);
    String delete(String id);
}
