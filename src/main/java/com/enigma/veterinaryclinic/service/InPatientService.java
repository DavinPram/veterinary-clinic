package com.enigma.veterinaryclinic.service;

import com.enigma.veterinaryclinic.entity.InPatient;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;

public interface InPatientService {
    InPatient register(InPatient inPatient);
    InPatient checkout(String id);
    InPatient getById(String id);
    Page<InPatient> getAll(Pageable pageable);
    String exportReport(String reportFormat) throws FileNotFoundException, JRException;
}
