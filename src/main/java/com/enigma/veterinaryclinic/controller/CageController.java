package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.dto.CageDTO;
import com.enigma.veterinaryclinic.entity.Cage;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.response.WebResponse;
import com.enigma.veterinaryclinic.service.CageService;
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
@RequestMapping("/cage")
public class CageController {

    @Autowired
    private CageService cageService;

    @PostMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<Cage>> create(@RequestBody Cage cage) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new WebResponse<>(String.format("Success, Data Has Been Insert"), cageService.create(cage)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<Cage>> getById(@PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Data Has Been Found"),cageService.getById(id)));
    }

    @GetMapping("/active")
    public ResponseEntity<WebResponse<PageResponse<Cage>>> getAllActive(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "cageName") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        CageDTO cageDTO = new CageDTO(null, null,false);
        Page<Cage> cages = cageService.getData(pageable, cageDTO);
        PageResponse<Cage> pageResponse = new PageResponse<>(
                cages.getContent(),
                cages.getTotalElements(),
                cages.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

    @GetMapping("/deleted")
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<PageResponse<Cage>>> getAllDeleted(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "cageName") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        CageDTO cageDTO = new CageDTO(null, null,true);
        Page<Cage> cages = cageService.getData(pageable, cageDTO);
        PageResponse<Cage> pageResponse = new PageResponse<>(
                cages.getContent(),
                cages.getTotalElements(),
                cages.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Cage>>> getAll(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "cageName") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Cage> cages = cageService.getAll(pageable);
        PageResponse<Cage> pageResponse = new PageResponse<>(
                cages.getContent(),
                cages.getTotalElements(),
                cages.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

    @GetMapping("/searchactive")
    public ResponseEntity<WebResponse<PageResponse<Cage>>> searchActive(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "cageName") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "name", required = false)String name
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        CageDTO cageDTO = new CageDTO(id, name,false);
        Page<Cage> cages = cageService.getData(pageable,cageDTO);

        PageResponse<Cage> pageResponse = new PageResponse<>(
                cages.getContent(),
                cages.getTotalElements(),
                cages.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(String.format("Data Cage is found"), pageResponse));
    }

    @GetMapping("/searchdelete")
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<PageResponse<Cage>>> searchDeleted(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "cageName") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "name", required = false)String name
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        CageDTO cageDTO = new CageDTO(id, name,true);
        Page<Cage> cages = cageService.getData(pageable,cageDTO);

        PageResponse<Cage> pageResponse = new PageResponse<>(
                cages.getContent(),
                cages.getTotalElements(),
                cages.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(String.format("Data Cage is found"), pageResponse));
    }

    @PutMapping
    public ResponseEntity<WebResponse<Cage>> update(@RequestBody Cage cage){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new WebResponse<>(String.format("Success, Data Has Been Update")
                        ,cageService.update(cage)));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE')")
//    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<String>> delete(@PathVariable("id") String id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(String.format("Success, Data Has Been Delete"),cageService.delete(id)));
    }
}
