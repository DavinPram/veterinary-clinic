package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.dto.TypeDTO;
import com.enigma.veterinaryclinic.entity.Type;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.TypeRepository;
import com.enigma.veterinaryclinic.service.TypeService;
import com.enigma.veterinaryclinic.specification.TypeSpecification;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class TypeServiceImpl implements TypeService {

  @Autowired
  private TypeRepository typeRepository;

  @Override
  public Type create(Type type) {
    return typeRepository.save(type);
  }

  @Override
  public Type getById(String id) {
    return typeRepository.getActiveType(id).orElseThrow(() -> new NotFoundException("Type not found!"));
  }

  @Override
  public Page<Type> list(Pageable pageable, TypeDTO typeDTO) {
    Specification<Type> specification = TypeSpecification.getSpecification(typeDTO);
    return typeRepository.findAll(specification, pageable);
  }

  @Override
  public Type update(Type type) {
    if (type.getIsDeleted() == null) {
      type.setIsDeleted(false);
    }
    findByIdOrThrowNotFound(type.getId());
    return typeRepository.save(type);
  }

  @Override
  public String delete(String id) {
    Type type = this.findByIdOrThrowNotFound(id);
    if (type.getIsDeleted()) {
      throw new NotFoundException("Type not found");
    }
    type.setIsDeleted(true);
    typeRepository.save(type);
    return "Type successfully deleted";
  }

  private Type findByIdOrThrowNotFound(String id) {
    Optional<Type> type = this.typeRepository.findById(id);
    if (type.isPresent()) {
      return type.get();
    } else {
      throw new NotFoundException("Type not found");
    }
  }
}
