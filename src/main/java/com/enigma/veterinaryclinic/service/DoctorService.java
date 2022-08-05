package com.enigma.veterinaryclinic.service;

import org.springframework.web.multipart.MultipartFile;
import com.enigma.veterinaryclinic.dto.DoctorDTO;
import com.enigma.veterinaryclinic.entity.Doctor;
import com.enigma.veterinaryclinic.response.DoctorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
  Doctor create(Doctor doctor);

  Doctor getById(String id);

  Page<Doctor> list(Pageable pageable, DoctorDTO doctorDTO);

  Doctor update(Doctor doctor);

  String delete(String id);

  DoctorResponse uploadImage(MultipartFile multipartFile, String id);

  DoctorResponse getProfile(String id);

  Doctor get(String id);
}
