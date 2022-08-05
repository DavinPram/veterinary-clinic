package com.enigma.veterinaryclinic.service;

import com.enigma.veterinaryclinic.dto.AnimalDTO;
import com.enigma.veterinaryclinic.entity.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalService {
    Animal create(Animal animal);
    Animal getById(String id);
    Page<Animal> getData(Pageable pageable, AnimalDTO animalDTO);
    Page<Animal> getAll(Pageable pageable);
    Animal update(Animal animal);
    String delete(String id);
}
