package com.enigma.veterinaryclinic.service;

import com.enigma.veterinaryclinic.dto.TypeDTO;
import com.enigma.veterinaryclinic.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

  Type create(Type type);

  Type getById(String id);

  Page<Type> list(Pageable pageable, TypeDTO typeDTO);

  Type update(Type type);

  String delete(String id);

}
