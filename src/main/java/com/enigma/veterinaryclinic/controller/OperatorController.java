package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.dto.OperatorDTO;
import com.enigma.veterinaryclinic.entity.Operator;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.response.WebResponse;
import com.enigma.veterinaryclinic.service.OperatorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "vetclinicapi")
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    //CRUD
    @PostMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<Operator>> create(@RequestBody Operator operator) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new WebResponse<>(String.format("Success, Data Has Been Insert"), operatorService.create(operator)));
    }
    @PutMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<Operator>> update(@RequestBody Operator operator){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new WebResponse<>(String.format("Success, Data Has Been Update")
                        ,operatorService.update(operator)));
    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<String>> delete(@PathVariable("id") String id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(String.format("Success, Data Has Been Delete"),operatorService.delete(id)));
    }
    //GET
    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<Operator>> getById(@PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Data Has Been Found"),operatorService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Operator>>> getAll(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Operator> operators = operatorService.getAll(pageable);
        PageResponse<Operator> pageResponse = new PageResponse<>(
                operators.getContent(),
                operators.getTotalElements(),
                operators.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

    @GetMapping("/active")
    public ResponseEntity<WebResponse<PageResponse<Operator>>> getAllActive(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        OperatorDTO operatorDTO = new OperatorDTO(null,null,false);
        Page<Operator> operators = operatorService.getData(pageable,operatorDTO);
        PageResponse<Operator> pageResponse = new PageResponse<>(
                operators.getContent(),
                operators.getTotalElements(),
                operators.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

    @GetMapping("/delete")
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<PageResponse<Operator>>> getAllDelete(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        OperatorDTO operatorDTO = new OperatorDTO(null,null,true);
        Page<Operator> operators = operatorService.getData(pageable,operatorDTO);
        PageResponse<Operator> pageResponse = new PageResponse<>(
                operators.getContent(),
                operators.getTotalElements(),
                operators.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

    //SEARCH
    @GetMapping("/searchactive")
    public ResponseEntity<WebResponse<PageResponse<Operator>>> searchActive(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "id",required = false)String id,
            @RequestParam(name = "name",required = false)String name
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        OperatorDTO operatorDTO = new OperatorDTO(id,name,false);
        Page<Operator> operators = operatorService.getData(pageable,operatorDTO);
        PageResponse<Operator> pageResponse = new PageResponse<>(
                operators.getContent(),
                operators.getTotalElements(),
                operators.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

    @GetMapping("/searchdelete")
    public ResponseEntity<WebResponse<PageResponse<Operator>>> searchDelete(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "id",required = false)String id,
            @RequestParam(name = "name",required = false)String name
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        OperatorDTO operatorDTO = new OperatorDTO(id,name,true);
        Page<Operator> operators = operatorService.getData(pageable,operatorDTO);
        PageResponse<Operator> pageResponse = new PageResponse<>(
                operators.getContent(),
                operators.getTotalElements(),
                operators.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }
}
