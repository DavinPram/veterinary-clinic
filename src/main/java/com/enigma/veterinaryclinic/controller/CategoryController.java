package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.dto.CategoryDTO;
import com.enigma.veterinaryclinic.entity.Category;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.response.WebResponse;
import com.enigma.veterinaryclinic.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.NoArgsConstructor;
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
@RequestMapping({"/categories"})
@NoArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<Category> createCategory(@RequestBody Category request) {
        Category category = this.categoryService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<Category> getById(@PathVariable("categoryId") Integer id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<PageResponse<Category>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "name", required = false) String name) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        CategoryDTO categoryDTO = new CategoryDTO(name);

        Page<Category> categories = this.categoryService.listWithPage(pageable, categoryDTO);

        PageResponse<Category> pageResponse = new PageResponse<>(
                categories.getContent(),
                categories.getTotalElements(),
                categories.getTotalPages(),
                page,
                size,
                sortBy
        );

        WebResponse<PageResponse<Category>> webResponse =
                new WebResponse<>("Data has been retrieved", pageResponse);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(webResponse);
    }


    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<Category> updateCategoryById(@RequestBody Category request) {
        Category update = this.categoryService.update(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(update);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<Category>> deleteCategoryById(@PathVariable("categoryId") String id) {
        Integer numberId = Integer.parseInt(id);
        Category category = categoryService.getCategoryById(numberId);
        String message = categoryService.delete(numberId);
        WebResponse<Category> webResponse = new WebResponse<>(message, category);
        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

}
