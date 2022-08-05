package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.dto.TypeDTO;
import com.enigma.veterinaryclinic.entity.Type;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.service.TypeService;
import com.enigma.veterinaryclinic.util.WebResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@SecurityRequirement(name = "vetclinicapi")
@RequestMapping({"/types"})
public class TypeController {

  @Autowired
  private TypeService typeService;

  public TypeController() {
  }

  @PostMapping
  public ResponseEntity<WebResponse<Type>> create(@RequestBody Type type) {
    Type type1 = typeService.create(type);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(new WebResponse<>("Type successfully created", type1));
  }

  @GetMapping({"/{typeId}"})
  public ResponseEntity<WebResponse<Type>> getById(@PathVariable("typeId") String id) {
    Type type1 = typeService.getById(id);
    WebResponse<Type> response = new WebResponse<>("Type found", type1);
    return ResponseEntity.status(HttpStatus.OK)
            .body(response);
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
  @SecurityRequirement(name = "vetclinicapi")
  public ResponseEntity<WebResponse<PageResponse<Type>>> list(
          @RequestParam(name = "size", defaultValue = "10") Integer size,
          @RequestParam(name = "page", defaultValue = "0") Integer page,
          @RequestParam(name = "type", required = false) String type,
          @RequestParam(name = "sortBy", defaultValue = "type") String sortBy
  ) {
    Pageable pageable = PageRequest.of(page, size);
    TypeDTO typeDTO = new TypeDTO(type);

    Page<Type> types = typeService.list(pageable, typeDTO);

    PageResponse<Type> pageResponse = new PageResponse<>(
            types.getContent(),
            types.getTotalElements(),
            types.getTotalPages(),
            page,
            size,
            sortBy
    );

    WebResponse<PageResponse<Type>> response = new WebResponse<>("Successfully get all data types", pageResponse);

    return ResponseEntity.status(HttpStatus.OK)
            .body(response);
  }

  @PutMapping
  public ResponseEntity<WebResponse<Type>> update(@RequestBody Type type) {
    Type type1 = this.typeService.update(type);
    WebResponse<Type> response = new WebResponse<>("Type successfully updated", type1);
    return ResponseEntity.status(HttpStatus.OK)
            .body(response);
  }

  @DeleteMapping({"/{typeId}"})
  public ResponseEntity<WebResponse<String>> delete(@PathVariable("typeId") String id) {
    String message = this.typeService.delete(id);
    WebResponse<String> response = new WebResponse<>(message, null);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

}
