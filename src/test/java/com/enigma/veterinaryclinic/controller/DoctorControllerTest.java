package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.entity.Doctor;
import com.enigma.veterinaryclinic.service.DoctorService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DoctorController.class)
@ActiveProfiles("test")
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @Autowired
    ObjectMapper objectMapper;

    private List<Doctor> doctorList;

    private Page<Doctor> doctorPage;

//    @BeforeEach
//    void setUp(){
//        this.doctorList = new ArrayList<>();
//        this.doctorList.add(new Doctor("123","davin","UB","lulusan terbaik","Sangat Berpengalaman","123123",null,new Date(),new Date(),false));
//        this.doctorList.add(new Doctor("124","kusnendi","UNDIP","Pinter","Berpengalaman","124124",null,new Date(),new Date(),false));
//        this.doctorList.add(new Doctor("234","damar","UTY","Rajin","Berpengalaman","234234",null,new Date(),new Date(),false));
//    }
//
//    public static String asJsonString(final Object obj){
//        try{
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e){
//            throw new RuntimeException();
//        }
//    }
//
//    @Test
//    void should_Success_UpdateDataDoctor() throws Exception{
//        given(doctorService.update(any(Doctor.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
//
//        Doctor doctor = new Doctor("123","davin","UB","lulusan terbaik","Sangat Berpengalaman","123123",null,new Date(),new Date(),false);
//
//        mockMvc.perform(put("/doctor")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(doctor)).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.id", Matchers.is(doctor.getId())));
//    }
//
//    @Test
//    void should_Success_DeleteDataDoctor() throws Exception{
//        String doctorId = "123";
//        Doctor doctor = new Doctor("123","davin","UB","lulusan terbaik","Sangat Berpengalaman","123123",null,new Date(),new Date(),false);
//        given(doctorService.getById(doctorId)).willReturn(doctor);
////        doNothing().when(DoctorService).delete(Doctor.getId());
//
//        this.mockMvc.perform(delete("/doctor/{id}",doctorId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message",Matchers.is("Success, Data Has Been Delete")));
//    }
//
//    @Test
//    void should_Success_CreateDataDoctor() throws Exception{
//        given(doctorService.create(any(Doctor.class))).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));
//
//        Doctor doctor = new Doctor("123","davin","UB","lulusan terbaik","Sangat Berpengalaman","123123",null,new Date(),new Date(),false);
//
//        this.mockMvc.perform(post("/doctor")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(doctor)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.id",Matchers.is(doctor.getId())));
//
//    }
}
