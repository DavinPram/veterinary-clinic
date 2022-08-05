package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.entity.InPatient;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.response.WebResponse;
import com.enigma.veterinaryclinic.service.InPatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/inpatient")
public class InPatientController {

    @Autowired
    private InPatientService inPatientService;

    @PostMapping("/register")
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<InPatient>> register(@RequestBody InPatient inPatient) {
        InPatient register = inPatientService.register(inPatient);
        WebResponse<InPatient> response = new WebResponse<>(
                "Registrasion success",
                register);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/checkout/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<InPatient>> checkout(@PathVariable("id") String id) {
        InPatient register = inPatientService.checkout(id);
        WebResponse<InPatient> response = new WebResponse<>(
                "checkout success",
                register);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/report/{format}")
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<String>> generatedReport(@PathVariable String format) throws JRException, FileNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Download Success"),inPatientService.exportReport(format)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<InPatient>> getById(@PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Data Has Been Found"),inPatientService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<InPatient>>> getAllData(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<InPatient> inPatients = inPatientService.getAll(pageable);
        PageResponse<InPatient> pageResponse = new PageResponse<>(
                inPatients.getContent(),
                inPatients.getTotalElements(),
                inPatients.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }
}
