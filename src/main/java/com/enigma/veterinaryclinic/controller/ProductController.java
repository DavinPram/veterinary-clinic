package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.dto.ProductDTO;
import com.enigma.veterinaryclinic.entity.Product;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.response.WebResponse;
import com.enigma.veterinaryclinic.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping({"/products"})
@NoArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<Product> createProduct(@RequestBody Product request) {
        Product product = this.productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getById(@PathVariable("productId") String id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Product>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice) {

        Sort sort =Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size,sort);
        ProductDTO productDTO = new ProductDTO(name,maxPrice,minPrice);

        Page<Product> categories = this.productService.listWithPage(pageable, productDTO);

        PageResponse<Product> pageResponse = new PageResponse<>(
                categories.getContent(),
                categories.getTotalElements(),
                categories.getTotalPages(),
                page,
                size,
                sortBy
        );
        WebResponse<PageResponse<Product>> webResponse =
                new WebResponse<>("Data has been retrieved",pageResponse);
        return ResponseEntity.status(HttpStatus.OK).body(webResponse);

    }

    @GetMapping("/customer-productList")
    public ResponseEntity<WebResponse<PageResponse<Product>>> customerProductList(
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice) {

        Sort sort =Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size,sort);
        ProductDTO productDTO = new ProductDTO(name,maxPrice,minPrice);

        Page<Product> categories = this.productService.getAll(pageable, productDTO);

        PageResponse<Product> pageResponse = new PageResponse<>(
                categories.getContent(),
                categories.getTotalElements(),
                categories.getTotalPages(),
                page,
                size,
                sortBy
        );
        WebResponse<PageResponse<Product>> webResponse =
                new WebResponse<>("Data has been retrieved",pageResponse);
        return ResponseEntity.status(HttpStatus.OK).body(webResponse);

    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<Product> updateProductById(@RequestBody Product request){
        Product update = this.productService.update(request);
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE','OPERATOR_ROLE', 'DOCTOR_ROLE')")
    @SecurityRequirement(name = "vetclinicapi")
    public ResponseEntity<WebResponse<Product>> deleteProductById(@PathVariable("productId") String id) {
        Product product = productService.getProductById(id);
        String message = productService.delete(id);
        WebResponse<Product> webResponse = new WebResponse<>(message, product);
        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
