package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.dto.AnimalDTO;
import com.enigma.veterinaryclinic.entity.Animal;
import com.enigma.veterinaryclinic.entity.Customer;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.security.jwt.AuthEntryPointJwt;
import com.enigma.veterinaryclinic.security.jwt.JwtUtils;
import com.enigma.veterinaryclinic.service.AnimalService;
import com.enigma.veterinaryclinic.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AnimalController.class)
@ActiveProfiles("test")
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    @MockBean
    UserService userService;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    JwtUtils jwtUtils;

    @Autowired
    ObjectMapper objectMapper;

    private List<Animal> animalList;

    private Page<Animal> animalPage;

    @BeforeEach
    void setUp(){
        this.animalList = new ArrayList<>();
        this.animalList.add(new Animal("123",null,"putih",8,null,new Date(),null,false,null));
        this.animalList.add(new Animal("124",null,"putih hitam",14,null,new Date(),null,false,null));
        this.animalList.add(new Animal("234",null,"hitam",5,null,new Date(),null,false,null));
    }

    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Test
    @WithMockUser(username = "davin", roles = "ADMIN_ROLE")
    void should_Success_UpdateDataAnimal() throws Exception{
        given(animalService.update(any(Animal.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Animal animal = new Animal("123",null,"putih",8,null,new Date(),null,false,null);

        mockMvc.perform(put("/animal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(animal)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", Matchers.is("Success, Data Has Been Update")))
                .andExpect(jsonPath("$.data.id", Matchers.is(animal.getId())));
    }

    @Test
    void should_Success_DeleteDataAnimal() throws Exception{
        String animalId = "123";
        Animal animal = new Animal("123",null,"putih",8,null,new Date(),null,false,null);
        given(animalService.getById(animalId)).willReturn(animal);
//        doNothing().when(animalService).delete(animal.getId());

        this.mockMvc.perform(delete("/animal/{id}",animalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message",Matchers.is("Success, Data Has Been Delete")));
    }

    @Test
    void should_Success_CreateDataAnimal() throws Exception{
        Animal animal = new Animal("123",null,"putih",8,null,new Date(),null,false,null);
        when(animalService.create(any(Animal.class))).thenReturn(animal);
        this.mockMvc.perform(post("/animal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(animal)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message",Matchers.is("Success, Data Has Been Insert")))
                .andExpect(jsonPath("$.data.id",Matchers.is(animal.getId())));
    }

    @Test
    void should_Success_GetDataById() throws Exception{
        when(animalService.getById("123")).thenReturn(animalList.get(0));

        RequestBuilder requestBuilder = get("/animal/123")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id",Matchers.is(animalList.get(0).getId())));
    }

    @Test
    void should_Success_GetAllDataAnimal() throws Exception{

        Sort sort = Sort.by(Sort.Direction.fromString("asc"), "name");
        Pageable pageable = PageRequest.of(0,10,sort);

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), animalList.size());
        animalPage = new PageImpl<>(animalList.subList(start,end), pageable, animalList.size());

        PageResponse<Animal> pageResponse = new PageResponse<>(
                animalPage.getContent(),
                animalPage.getTotalElements(),
                animalPage.getTotalPages(),
                0,
                10,
                "name");

        given(animalService.getAll(pageable)).willReturn(animalPage);

        RequestBuilder requestBuilder =get("/animal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pageResponse));

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message",Matchers.is("Success")));
    }

//    @Test
    void should_Success_GetAllDataActiveAnimal() throws Exception{
        Sort sort = Sort.by(Sort.Direction.fromString("asc"), "name");
        Pageable pageable = PageRequest.of(0,10,sort);
        AnimalDTO animalDTO = new AnimalDTO(null,null,null,null,false);

//        final int start = (int)pageable.getOffset();
//        final int end = Math.min((start + pageable.getPageSize()), animalList.size());
        animalPage = new PageImpl<>(animalList);

        PageResponse<Animal> pageResponse = new PageResponse<>(
                animalPage.getContent(),
                animalPage.getTotalElements(),
                animalPage.getTotalPages(),
                0,
                10,
                "name");

        given(animalService.getData(pageable,animalDTO)).willReturn(animalPage);

        RequestBuilder requestBuilder =get("/animal/active")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pageResponse));

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message",Matchers.is("Success")));
    }
}
