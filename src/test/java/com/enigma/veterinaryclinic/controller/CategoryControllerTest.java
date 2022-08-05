package com.enigma.veterinaryclinic.controller;


import com.enigma.veterinaryclinic.entity.Category;
import com.enigma.veterinaryclinic.security.WebConfigSecurity;
import com.enigma.veterinaryclinic.security.jwt.AuthEntryPointJwt;
import com.enigma.veterinaryclinic.security.jwt.AuthTokenFilter;
import com.enigma.veterinaryclinic.security.jwt.JwtUtils;
import com.enigma.veterinaryclinic.service.CategoryService;
import com.enigma.veterinaryclinic.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@ActiveProfiles("test")
public class CategoryControllerTest {
    @MockBean
    CategoryService categoryService;

    @MockBean
    UserService userService;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    JwtUtils jwtUtils;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private List<Category> categoryList;

    private Page<Category> categoryPage;

    @BeforeEach
    void setUp(){
        this.categoryList = new ArrayList<>();
        this.categoryList.add(new Category(1,"A",new Date(),new Date(),null));
        this.categoryList.add(new Category(2,"B",new Date(),new Date(),null));
        this.categoryList.add(new Category(3,"C",new Date(),new Date(),null));
    }

    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Test
//    @WithMockUser(username = "damario", roles = "ADMIN_ROLE")
    void should_Success_UpdateDataCage() throws Exception{
        given(categoryService.update(any(Category.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Category category = new Category(1,"B",new Date(),new Date(),null);

        mockMvc.perform(put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id",Matchers.is(category.getId())));
    }
//
//    @Test
//    void should_Success_DeleteDataCage() throws Exception{
//        String CageId = "123";
//        Cage Cage = new Cage("124","2",false,new Date(),new Date(),false,null);
//        given(cageService.getById(CageId)).willReturn(Cage);
////        doNothing().when(CageService).delete(Cage.getId());
//
//        this.mockMvc.perform(delete("/cage/{id}",CageId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message",Matchers.is("Success, Data Has Been Delete")));
//    }
//
//    @Test
//    void should_Success_CreateDataCage() throws Exception{
//        given(cageService.create(any(Cage.class))).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));
//
//        Cage Cage = new Cage("124","2",false,new Date(),new Date(),false,null);
//
//        this.mockMvc.perform(post("/cage")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(Cage)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.message",Matchers.is("Success, Data Has Been Insert")))
//                .andExpect(jsonPath("$.data.id",Matchers.is(Cage.getId())));
//
//    }
//
//    @Test
//    void should_Success_GetDataById() throws Exception{
//        when(cageService.getById("123")).thenReturn(cageList.get(0));
//
//        RequestBuilder requestBuilder = get("/cage/123")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.data.id",Matchers.is(cageList.get(0).getId())));
//    }
//
//    @Test
//    void should_Success_GetAllDataCage() throws Exception{
//
//        Sort sort = Sort.by(Sort.Direction.fromString("asc"), "cageName");
//        Pageable pageable = PageRequest.of(0,10,sort);
//
//        final int start = (int)pageable.getOffset();
//        final int end = Math.min((start + pageable.getPageSize()), cageList.size());
//        cagePage = new PageImpl<>(cageList.subList(start,end), pageable, cageList.size());
//
//        PageResponse<Cage> pageResponse = new PageResponse<>(
//                cagePage.getContent(),
//                cagePage.getTotalElements(),
//                cagePage.getTotalPages(),
//                0,
//                10,
//                "cageName");
//
//        given(cageService.getAll(pageable)).willReturn(cagePage);
//
//        RequestBuilder requestBuilder =get("/cage")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(pageResponse));
//
//        this.mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message",Matchers.is("Success")));
//    }



}


