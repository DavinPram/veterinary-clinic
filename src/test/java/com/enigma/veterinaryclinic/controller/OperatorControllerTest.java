package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.entity.Operator;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.service.OperatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = OperatorController.class)
@ActiveProfiles("test")
public class OperatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperatorService OperatorService;

    @Autowired
    ObjectMapper objectMapper;

    private List<Operator> operatorList;

    private Page<Operator> operatorPage;

//    @BeforeEach
//    void setUp(){
//        this.operatorList = new ArrayList<>();
//        this.operatorList.add(new Operator("123","1",new Date(),new Date(),false,null));
//        this.operatorList.add(new Operator("124","2",new Date(),new Date(),false,null));
//        this.operatorList.add(new Operator("234","3",new Date(),new Date(),false,null));
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
//    @Test
//    void should_Success_DeleteDataOperator() throws Exception{
//        String OperatorId = "123";
//        Operator Operator = new Operator("123","1",new Date(),new Date(),false,null);
//        given(OperatorService.getById(OperatorId)).willReturn(Operator);
////        doNothing().when(OperatorService).delete(Operator.getId());
//
//        this.mockMvc.perform(delete("/operator/{id}",OperatorId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message",Matchers.is("Success, Data Has Been Delete")));
//    }
//
//    @Test
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
//
//    }
//
//    @Test
//    void should_Success_GetDataById() throws Exception{
//        when(OperatorService.getById("123")).thenReturn(operatorList.get(0));
//
//        RequestBuilder requestBuilder = get("/operator/123")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.data.id",Matchers.is(operatorList.get(0).getId())));
//    }
//
//    @Test
//    void should_Success_GetAllDataOperator() throws Exception{
//
//        Sort sort = Sort.by(Sort.Direction.fromString("asc"), "name");
//        Pageable pageable = PageRequest.of(0,10,sort);
//
//        final int start = (int)pageable.getOffset();
//        final int end = Math.min((start + pageable.getPageSize()), operatorList.size());
//        operatorPage = new PageImpl<>(operatorList.subList(start,end), pageable, operatorList.size());
//
//        PageResponse<Operator> pageResponse = new PageResponse<>(
//                operatorPage.getContent(),
//                operatorPage.getTotalElements(),
//                operatorPage.getTotalPages(),
//                0,
//                10,
//                "name");
//
//        given(OperatorService.getAll(pageable)).willReturn(operatorPage);
//
//        RequestBuilder requestBuilder =get("/operator")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(pageResponse));
//
//        this.mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message",Matchers.is("Success")));
//    }
}
