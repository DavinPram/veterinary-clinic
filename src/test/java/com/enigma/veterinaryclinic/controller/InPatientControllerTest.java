package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.entity.Animal;
import com.enigma.veterinaryclinic.entity.Operator;
import com.enigma.veterinaryclinic.service.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InPatientController.class)
@ActiveProfiles("test")
public class InPatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    @Autowired
    ObjectMapper objectMapper;

    private List<Animal> animalList;

    private Page<Animal> animalPage;

//    @BeforeEach
//    void setUp(){
//        this.animalList = new ArrayList<>();
//        this.animalList.add(new Animal("123","putih",8,"kucing",new Date(),null,false,null));
//        this.animalList.add(new Animal("124","putih hitam",14,"kucing",new Date(),null,false,null));
//        this.animalList.add(new Animal("234","hitam",5,"kucing",new Date(),null,false,null));
//    }
//
//    public static String asJsonString(final Object obj){
//        try{
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e){
//            throw new RuntimeException();
//        }
//    }

////    @Test
//    void should_Success_CreateDataOperator() throws Exception{
//        given(OperatorService.create(any(Operator.class))).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));
//
//        Operator Operator = new Operator("123","1",new Date(),new Date(),false,null);
//
//        this.mockMvc.perform(post("/operator")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(Operator)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.message",Matchers.is("Success, Data Has Been Insert")))
//                .andExpect(jsonPath("$.data.id",Matchers.is(Operator.getId())));
//    }
//
////    @Test
//    void should_Success_UpdateDataOperator() throws Exception{
//        given(OperatorService.update(any(Operator.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
//
//        Operator Operator = new Operator("123","1",new Date(),new Date(),false,null);
//
//        mockMvc.perform(put("/operator")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(Operator)).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.id", Matchers.is(Operator.getId())));
//    }
//
////    @Test
//    void should_Success_GetDataById() throws Exception{
//        when(OperatorService.getById("123")).thenReturn(operatorList.get(0));
//
//        RequestBuilder requestBuilder = get("/operator/123")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.data.id", Matchers.is(operatorList.get(0).getId())));
//    }
}
