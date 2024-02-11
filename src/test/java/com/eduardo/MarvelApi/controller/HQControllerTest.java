package com.eduardo.MarvelApi.controller;

import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.exception.HQAlreadyRegistered;
import com.eduardo.MarvelApi.exception.ResourceNotFoundException;
import com.eduardo.MarvelApi.services.HQService;
import com.eduardo.MarvelApi.util.HQTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(HQController.class)
@AutoConfigureMockMvc
public class HQControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    HQService service;

    @Autowired
    private ObjectMapper objectMapper;

    HQDTO mockHQDto = HQTestUtil.createHQDTO();

    @Test
    public void testRegisterHQ_Success() throws Exception {
        when(service.registerHQ(any())).thenReturn(mockHQDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/hq")
                        .content(objectMapper.writeValueAsString(mockHQDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("WOLVERINE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.summary").value("SUMMARY"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.yearOfPublication").value(2024))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("example_image.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(20.99))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("OUTROS"));
    }

    @Test
    public void testRegisterHQ_AlreadyRegistered() throws Exception {
        when(service.registerHQ(any())).thenThrow(HQAlreadyRegistered.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/hq")
                        .content(objectMapper.writeValueAsString(mockHQDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
        verify(service, times(1)).registerHQ(any(mockHQDto.getClass()));
    }

    @Test
    public void testGetAll() throws Exception {
        List<HQDTO> hqDTOList = Arrays.asList(
                HQTestUtil.createHQDTO(),
                HQTestUtil.createHQDTO(),
                HQTestUtil.createHQDTO()
        );

        Page<HQDTO> page = new PageImpl<>(hqDTOList);

        when(service.findAll(anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/hq/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name", Matchers.is("Example HQ")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name", Matchers.is("Example HQ")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].name", Matchers.is("Example HQ")));
    }

    @Test
    public void testFindByName_Success() throws Exception {
        when(service.findByName(anyString())).thenReturn(mockHQDto);


        mockMvc.perform(MockMvcRequestBuilders.get("/hq")
                        .param("name", "Example HQ"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockHQDto)));
    }

    @Test
    public void testFindByName_NotFound() throws Exception {
        when(service.findByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/hq")
                        .param("name", "Example HQ"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
