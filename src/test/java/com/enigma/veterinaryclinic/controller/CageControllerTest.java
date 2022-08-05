package com.enigma.veterinaryclinic.controller;

import com.enigma.veterinaryclinic.entity.Cage;
import com.enigma.veterinaryclinic.response.PageResponse;
import com.enigma.veterinaryclinic.service.CageService;
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

@WebMvcTest(controllers = CageController.class)
@ActiveProfiles("test")
public class CageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CageService cageService;

    @Autowired
    ObjectMapper objectMapper;

    private List<Cage> cageList;

    private Page<Cage> cagePage;

    @BeforeEach
    void setUp(){
        this.cageList = new ArrayList<>();
        this.cageList.add(new Cage("123","1",false,new Date(),new Date(),false,null));
        this.cageList.add(new Cage("124","2",false,new Date(),new Date(),false,null));
        this.cageList.add(new Cage("234","3",false,new Date(),new Date(),false,null));
    }

    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Test
    void should_Success_UpdateDataCage() throws Exception{
        given(cageService.update(any(Cage.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Cage Cage = new Cage("124","2",false,new Date(),new Date(),false,null);

        mockMvc.perform(put("/cage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(Cage)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", Matchers.is(Cage.getId())));
    }

    @Test
    void should_Success_DeleteDataCage() throws Exception{
        String CageId = "123";
        Cage Cage = new Cage("124","2",false,new Date(),new Date(),false,null);
        given(cageService.getById(CageId)).willReturn(Cage);
//        doNothing().when(CageService).delete(Cage.getId());

        this.mockMvc.perform(delete("/cage/{id}",CageId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message",Matchers.is("Success, Data Has Been Delete")));
    }

    @Test
    void should_Success_CreateDataCage() throws Exception{
        given(cageService.create(any(Cage.class))).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        Cage Cage = new Cage("124","2",false,new Date(),new Date(),false,null);

        this.mockMvc.perform(post("/cage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(Cage)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message",Matchers.is("Success, Data Has Been Insert")))
                .andExpect(jsonPath("$.data.id",Matchers.is(Cage.getId())));

    }

    @Test
    void should_Success_GetDataById() throws Exception{
        when(cageService.getById("123")).thenReturn(cageList.get(0));

        RequestBuilder requestBuilder = get("/cage/123")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id",Matchers.is(cageList.get(0).getId())));
    }

    @Test
    void should_Success_GetAllDataCage() throws Exception{

        Sort sort = Sort.by(Sort.Direction.fromString("asc"), "cageName");
        Pageable pageable = PageRequest.of(0,10,sort);

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), cageList.size());
        cagePage = new PageImpl<>(cageList.subList(start,end), pageable, cageList.size());

        PageResponse<Cage> pageResponse = new PageResponse<>(
                cagePage.getContent(),
                cagePage.getTotalElements(),
                cagePage.getTotalPages(),
                0,
                10,
                "cageName");

        given(cageService.getAll(pageable)).willReturn(cagePage);

        RequestBuilder requestBuilder =get("/cage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pageResponse));

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message",Matchers.is("Success")));
    }
}
