package com.enigma.veterinaryclinic.service.impl;

import com.enigma.veterinaryclinic.dto.InPatientReportDTO;
import com.enigma.veterinaryclinic.entity.Animal;
import com.enigma.veterinaryclinic.entity.Cage;
import com.enigma.veterinaryclinic.entity.InPatient;
import com.enigma.veterinaryclinic.exception.BadRequestException;
import com.enigma.veterinaryclinic.exception.NotFoundException;
import com.enigma.veterinaryclinic.repository.InPatientRepository;
import com.enigma.veterinaryclinic.service.CageService;
import com.enigma.veterinaryclinic.service.InPatientService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
@Transactional
public class InPatientServiceImpl implements InPatientService {

    @Autowired
    private InPatientRepository inPatientRepository;

    @Autowired
    private CageService cageService;

    @Override
    public InPatient register(InPatient inPatient) {
        InPatient save = inPatientRepository.save(inPatient);

        Cage cage = save.getCage();
        Cage cage1 = cageService.getById(cage.getId());
        if (cage1.getIsUsed()==true) throw new BadRequestException("Cage Is Used");
        cage1.setIsUsed(true);
        cageService.update(cage1);

        return save;
    }

    @Override
    public InPatient checkout(String id) {
        InPatient save = findByIdOrThrowNotFound(id);
        save.setPayment(true);
        save.setCheckoutAt(new Date());
        Cage saveCage = cageService.getById(save.getCage().getId());
        saveCage.setIsUsed(false);
        cageService.update(saveCage);
        return inPatientRepository.save(save);
    }

    @Override
    public InPatient getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    private InPatient findByIdOrThrowNotFound(String id){
        Optional<InPatient> inPatient = inPatientRepository.findById(id);
        if (inPatient.isPresent()) return inPatient.get();
        else throw new NotFoundException(String.format("Animal With Id %s Not Found", id));
    }

    @Override
    public Page<InPatient> getAll(Pageable pageable) {
        return inPatientRepository.findAll(pageable);
    }

    @Override
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\Public\\Downloads";

        List<InPatient> sort = inPatientRepository.findAll();
        List<InPatientReportDTO> inPatients = new ArrayList<>();

        for (InPatient inPatient: sort) {
            InPatientReportDTO inPatientReportDTO = new InPatientReportDTO(
                    inPatient.getDoctor().getName(),
                    inPatient.getOperator().getName(),
                    inPatient.getAnimal().getName(),
                    inPatient.getCage().getCageName(),
                    inPatient.getDescription(),
                    inPatient.getPayment(),
                    inPatient.getPrice(),
                    inPatient.getCheckoutAt(),
                    inPatient.getStatus(),
                    inPatient.getCreatedAt()
            );
            inPatients.add(inPatientReportDTO);
        }

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:inpatientreport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(inPatients);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("createdBy","Team 5 KUDDASYI");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        if (reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\inpatientreport.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\inpatientreport.pdf");
        }
        return "report generated in path : " + path;
    }
}
