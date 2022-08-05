package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.dto.DoctorDTO;
import com.enigma.veterinaryclinic.entity.Doctor;
import com.enigma.veterinaryclinic.response.DoctorResponse;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.service.DoctorService;
import com.enigma.veterinaryclinic.util.WebResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@SecurityRequirement(name = "vetclinicapi")
@RequestMapping({"/doctors"})
public class DoctorController {
  @Autowired
  private DoctorService doctorService;

  public DoctorController() {
  }

  @PostMapping()
  @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
  @SecurityRequirement(name = "vetclinicapi")
  public ResponseEntity<WebResponse<Doctor>> create(@RequestBody Doctor doctor) {
    Doctor doctor1 = doctorService.create(doctor);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(new WebResponse<>("Doctor successfully created", doctor1));
  }

  @PutMapping
  @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
  @SecurityRequirement(name = "vetclinicapi")
  public ResponseEntity<WebResponse<Doctor>> update(@RequestBody Doctor doctor) {
    Doctor doctor1 = doctorService.update(doctor);
    return ResponseEntity.status(HttpStatus.OK)
            .body(new WebResponse<>("Doctor successfully updated", doctor1));
  }

  @PutMapping(
          consumes = {
                  MediaType.APPLICATION_JSON_VALUE,
                  MediaType.MULTIPART_FORM_DATA_VALUE
          },
          produces = "application/json",
          value = "/{id}"
  )
  @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
  @SecurityRequirement(name = "vetclinicapi")
  public ResponseEntity<WebResponse<DoctorResponse>> uploadImageDoctor(
          @RequestPart(name = "file") MultipartFile multipartFile,
          @PathVariable("id") String id
  ) {
    DoctorResponse doctorResponse = doctorService.uploadImage(multipartFile, id);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new WebResponse<>("Successfully upload Image", doctorResponse));
  }

  @DeleteMapping({"/{doctorId}"})
  @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
  @SecurityRequirement(name = "vetclinicapi")
  public ResponseEntity<WebResponse<String>> delete(@PathVariable("doctorId") String id) {
    String message = this.doctorService.delete(id);
    WebResponse<String> response = new WebResponse<>(message, null);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/image/{id}")
  public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
    Doctor file = doctorService.get(id);

    return ResponseEntity
            .status(HttpStatus.OK)
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\""
                            + file.getName() + "\"")
            .body(file.getImage());
  }

  @GetMapping({"/{doctorId}"})
  public ResponseEntity<WebResponse<Doctor>> getById(@PathVariable("doctorId") String id) {
    Doctor doctor = doctorService.getById(id);
    WebResponse<Doctor> response = new WebResponse<>("Doctor found", doctor);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/profile/{id}")
  public ResponseEntity<WebResponse<DoctorResponse>> getProfile(@PathVariable("id") String id){
    DoctorResponse doctorResponse = doctorService.getProfile(id);
    WebResponse<DoctorResponse> response = new WebResponse<>("Doctor found",doctorResponse);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping
  public ResponseEntity<WebResponse<PageResponse<DoctorResponse>>> list(
          @RequestParam(name = "size", defaultValue = "10") Integer size,
          @RequestParam(name = "page", defaultValue = "0") Integer page,
          @RequestParam(name = "name", required = false) String name,
          @RequestParam(name = "alumni", required = false) String alumni,
          @RequestParam(name = "experience", required = false) String experience,
          @RequestParam(name = "sortBy", defaultValue = "name") String sortBy
  ) {
    Pageable pageable = PageRequest.of(page, size);
    DoctorDTO doctorDTO = new DoctorDTO(name, alumni, experience);

    Page<Doctor> doctors = doctorService.list(pageable, doctorDTO);

    List<Doctor> doctor = doctors.getContent();
    List<DoctorResponse> result = new ArrayList<>();
    for (Doctor data: doctor) {
      String fileDownloadUri;
      DoctorResponse response = new DoctorResponse(
              data.getId(),
              data.getName(),
              data.getAlumni(),
              data.getDescription(),
              data.getExperience(),
              data.getSip(),
              fileDownloadUri = ServletUriComponentsBuilder
              .fromCurrentContextPath()
              .path("/doctors/image/")
              .path(data.getId())
              .toUriString()
      );
      result.add(response);
    }

    Page<DoctorResponse> responses = new PageImpl<>(result);

    PageResponse<DoctorResponse> pageResponse = new PageResponse<>(
            responses.getContent(),
            responses.getTotalElements(),
            responses.getTotalPages(),
            page,
            size,
            sortBy
    );

    WebResponse<PageResponse<DoctorResponse>> response = new WebResponse<>("Successfully get all data doctors", pageResponse);

    return ResponseEntity.status(HttpStatus.OK)
            .body(response);
  }

}
