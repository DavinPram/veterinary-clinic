package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.dto.DoctorDTO;
import com.enigma.veterinaryclinic.entity.Doctor;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.DoctorRepository;
import com.enigma.veterinaryclinic.response.DoctorResponse;
import com.enigma.veterinaryclinic.service.DoctorService;
import com.enigma.veterinaryclinic.specification.DoctorSpecification;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.print.Doc;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DoctorServiceImpl implements DoctorService {

  @Autowired
  private DoctorRepository doctorRepository;


  @Override
  public Doctor create(Doctor doctor) {
    return doctorRepository.save(doctor);
  }

  @Override
  public Doctor getById(String id) {

    return doctorRepository.getActive(id).orElseThrow(() -> new NotFoundException("Doctor not found"));
  }

  @Override
  public Page<Doctor> list(Pageable pageable, DoctorDTO doctorDTO) {
    Specification<Doctor> specification = DoctorSpecification.getSpecification(doctorDTO);

    return doctorRepository.findAll(specification, pageable);
  }

  @Override
  public Doctor update(Doctor doctor) {
    if (doctor.getIsDeleted() == null) {
      doctor.setIsDeleted(false);
    }
    findByIdOrThrowNotFound(doctor.getId());
    return doctorRepository.save(doctor);
  }

  @Override
  public String delete(String id) {
    Doctor doctor = this.findByIdOrThrowNotFound(id);
    if(doctor.getIsDeleted()) {
      throw new NotFoundException("Doctor not found");
    }
    doctor.setIsDeleted(true);
    doctorRepository.save(doctor);
    return "Doctor Successfully deleted";
  }

  @Override
  public DoctorResponse uploadImage(MultipartFile multipartFile, String id) {
//    String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    DoctorResponse doctorResponse = null;

    try {
      Doctor doctor = findByIdOrThrowNotFound(id);
      if(doctor.getIsDeleted()) {
        throw new NotFoundException("Doctor not found");
      }
      doctor.setImage(multipartFile.getBytes());

      Doctor save = doctorRepository.save(doctor);

      String fileDownloadUri = ServletUriComponentsBuilder
              .fromCurrentContextPath()
              .path("/doctors/image/")
              .path(save.getId())
              .toUriString();

      doctorResponse = new DoctorResponse(null, save.getName(), save.getAlumni(), save.getDescription(), save.getExperience(), save.getSip(), fileDownloadUri);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return doctorResponse;
  }

  @Override
  public DoctorResponse getProfile(String id) {
    Doctor doctor = findByIdOrThrowNotFound(id);
    if(doctor.getIsDeleted()) {
      throw new NotFoundException("Doctor not found");
    }
    String fileUploadUri = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/doctors/image/")
            .path(doctor.getId())
            .toUriString();
    return new DoctorResponse(doctor.getId(),doctor.getName(),doctor.getAlumni(),doctor.getDescription(),doctor.getExperience(),doctor.getSip(),fileUploadUri);
  }

  @Override
  public Doctor get(String id) {
    return doctorRepository.findById(id).orElseThrow(() -> new NotFoundException("Doctor Not Found"));
  }

  private Doctor findByIdOrThrowNotFound(String id) {
    Optional<Doctor> doctor = this.doctorRepository.findById(id);
    if (doctor.isPresent()) {
      return doctor.get();
    } else {
      throw new NotFoundException("Doctor not found");
    }
  }
}
