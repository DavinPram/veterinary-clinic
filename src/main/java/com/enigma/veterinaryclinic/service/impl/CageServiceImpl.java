package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.dto.CageDTO;
import com.enigma.veterinaryclinic.entity.Cage;
import com.enigma.veterinaryclinic.exception.BadRequestException;
import com.enigma.veterinaryclinic.exception.MethodNotAllowedException;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.CageRepository;
import com.enigma.veterinaryclinic.service.CageService;
import com.enigma.veterinaryclinic.specification.CageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CageServiceImpl implements CageService {

    @Autowired
    private CageRepository cageRepository;

    @Override
    public Cage create(Cage cage) {
        if (cage.getCageName()==null || cage.getCageName().isEmpty() || cage.getCageName().equalsIgnoreCase("")) throw new BadRequestException(String.format("Cage Name is Required"));
        else return cageRepository.save(cage);
    }

    @Override
    public Cage getById(String id) {
        Cage cage = findByIdOrThrowNotFound(id);
        if (cage.getDeleteAt()!=null){
            throw new NotFoundException(String.format("Cage With Id %s Not Found",cage.getId()));
        } else {
            return cage;
        }
    }

    @Override
    public Page<Cage> getData(Pageable pageable, CageDTO cageDTO) {
        Specification<Cage> specification = CageSpecification.getSpecification(cageDTO);
        return cageRepository.findAll(specification,pageable);
    }

    @Override
    public Page<Cage> getAll(Pageable pageable) {
        return cageRepository.findAll(pageable);
    }

    @Override
    public Cage update(Cage cage) {
        findByIdOrThrowNotFound(cage.getId());
        if (cage.getIsDelete()==null) cage.setIsDelete(false);
        return cageRepository.save(cage);
    }

    @Override
    public String delete(String id) {
        if (id.isEmpty() || id==null || id.equalsIgnoreCase("")) throw new MethodNotAllowedException("Need Id!");
        Cage cage = findByIdOrThrowNotFound(id);
        if (cage.getIsDelete()==true) {
            throw new NotFoundException(String.format("Cage With Id %s Not Found",cage.getId()));
        }
        cage.setIsDelete(true);
        cage.setDeleteAt(new Date());
        cageRepository.save(cage);
        return String.format("Cage with id %s is Deleted", id);
    }
    
    private Cage findByIdOrThrowNotFound(String id){
        Optional<Cage> cage = cageRepository.findById(id);
        if (cage.isPresent()) return cage.get();
        else throw new NotFoundException(String.format("Cage with id %s not found", id));
    }
}
