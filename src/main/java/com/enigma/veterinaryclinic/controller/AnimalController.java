package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.dto.AnimalDTO;
import com.enigma.veterinaryclinic.entity.Animal;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.response.WebResponse;
import com.enigma.veterinaryclinic.service.AnimalService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    //CRUD
    @PostMapping
    public ResponseEntity<WebResponse<Animal>> create(@RequestBody Animal animal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new WebResponse<>(String.format("Success, Data Has Been Insert"), animalService.create(animal)));
    }

    @PutMapping
    public ResponseEntity<WebResponse<Animal>> update(@RequestBody Animal animal){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new WebResponse<>(String.format("Success, Data Has Been Update")
                        ,animalService.update(animal)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<String>> delete(@PathVariable("id") String id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>(String.format("Success, Data Has Been Delete"),animalService.delete(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<Animal>> getById(@PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Data Has Been Found"),animalService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Animal>>> getAll(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Animal> animals = animalService.getAll(pageable);
        PageResponse<Animal> pageResponse = new PageResponse<>(
                animals.getContent(),
                animals.getTotalElements(),
                animals.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

    @GetMapping("/active")
    public ResponseEntity<WebResponse<PageResponse<Animal>>> getAllActive(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        AnimalDTO animalDTO = new AnimalDTO(null,null,null,null,false);
        Page<Animal> animals = animalService.getData(pageable,animalDTO);
        PageResponse<Animal> pageResponse = new PageResponse<>(
                animals.getContent(),
                animals.getTotalElements(),
                animals.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

    @GetMapping("/delete")
    public ResponseEntity<WebResponse<PageResponse<Animal>>> getAllDelete(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        AnimalDTO animalDTO = new AnimalDTO(null,null,null,null,true);
        Page<Animal> animals = animalService.getData(pageable,animalDTO);
        PageResponse<Animal> pageResponse = new PageResponse<>(
                animals.getContent(),
                animals.getTotalElements(),
                animals.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

    //SEARCH
    @GetMapping("/searchactive")
    public ResponseEntity<WebResponse<PageResponse<Animal>>> searchAllActive(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "id",required = false)String id,
            @RequestParam(name = "name",required = false)String name,
            @RequestParam(name = "minage",required = false)Integer minage,
            @RequestParam(name = "maxage",required = false)Integer maxage
//            @RequestParam(name = "type",required = false)String type
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        AnimalDTO animalDTO = new AnimalDTO(id,name,minage,maxage,false);
        Page<Animal> animals = animalService.getData(pageable,animalDTO);
        PageResponse<Animal> pageResponse = new PageResponse<>(
                animals.getContent(),
                animals.getTotalElements(),
                animals.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

    @GetMapping("/searchdelete")
    public ResponseEntity<WebResponse<PageResponse<Animal>>> searchAllDelete(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "id",required = false)String id,
            @RequestParam(name = "name",required = false)String name,
            @RequestParam(name = "minage",required = false)Integer minage,
            @RequestParam(name = "maxage",required = false)Integer maxage
//            @RequestParam(name = "type",required = false)String type
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        AnimalDTO animalDTO = new AnimalDTO(id,name,minage,maxage,true);
        Page<Animal> animals = animalService.getData(pageable,animalDTO);
        PageResponse<Animal> pageResponse = new PageResponse<>(
                animals.getContent(),
                animals.getTotalElements(),
                animals.getTotalPages(),
                page,
                size,
                sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(String.format("Success"),pageResponse));
    }

}
