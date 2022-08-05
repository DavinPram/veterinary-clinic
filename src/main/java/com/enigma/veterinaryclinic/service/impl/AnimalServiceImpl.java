package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.dto.AnimalDTO;
import com.enigma.veterinaryclinic.entity.Animal;
import com.enigma.veterinaryclinic.exception.BadRequestException;
import com.enigma.veterinaryclinic.exception.MethodNotAllowedException;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.AnimalRepository;
import com.enigma.veterinaryclinic.service.AnimalService;
import com.enigma.veterinaryclinic.specification.AnimalSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public Animal create(Animal animal) {
        if (animal.getName()==null || animal.getName().equalsIgnoreCase("") || animal.getName().isEmpty() ||
        animal.getAge()==null || animal.getAge().equals(""))
            throw new BadRequestException("The Sent JSON Has Flaws");
        else return animalRepository.save(animal);
    }

    @Override
    public Animal getById(String id) {
        Animal animal = findByIdOrThrowNotFound(id);
        if (animal.getIsDeleted()==true) throw new NotFoundException(String.format("Animal With Id %s Not Found",id));
        else return animal;
    }

    @Override
    public Page<Animal> getData(Pageable pageable, AnimalDTO animalDTO) {
        Specification<Animal> specification = AnimalSpecification.getSpecification(animalDTO);
        return animalRepository.findAll(specification, pageable);
    }

    @Override
    public Page<Animal> getAll(Pageable pageable) {
        return animalRepository.findAll(pageable);
    }

    @Override
    public Animal update(Animal animal) {
        findByIdOrThrowNotFound(animal.getId());
        if (animal.getIsDeleted()==null) animal.setIsDeleted(false);
        return animalRepository.save(animal);
    }

    @Override
    public String delete(String id) {
        if (id.isEmpty() || id==null || id.equalsIgnoreCase("")) throw new MethodNotAllowedException("Need Id!");
        Animal animal = findByIdOrThrowNotFound(id);
        if (animal.getIsDeleted()==true) throw new NotFoundException(String.format("Animal With Id %s Not Found", animal.getId()));
        animal.setIsDeleted(true);
        animal.setDeletedAt(new Date());
        animalRepository.save(animal);
        return String.format("Animal With Id %s is Deleted",id);
    }

    private Animal findByIdOrThrowNotFound(String id){
        Optional<Animal> animal = animalRepository.findById(id);
        if (animal.isPresent()) return animal.get();
        else throw new NotFoundException(String.format("Animal With Id %s Not Found", id));
    }
}
