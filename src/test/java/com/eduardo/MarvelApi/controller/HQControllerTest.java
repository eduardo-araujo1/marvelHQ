package com.eduardo.MarvelApi.controller;

import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.exception.HQAlreadyRegistered;
import com.eduardo.MarvelApi.exception.ResourceNotFoundException;
import com.eduardo.MarvelApi.enums.Category;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.author").value("WOLVERINE"))
                .andExpect(jsonPath("$.summary").value("SUMMARY"))
                .andExpect(jsonPath("$.yearOfPublication").value(2024))
                .andExpect(jsonPath("$.image").value("example_image.jpg"))
                .andExpect(jsonPath("$.price").value(20.99))
                .andExpect(jsonPath("$.category").value("OUTROS"));
    }

    @Test
    public void testRegisterHQ_AlreadyRegistered() throws Exception {
        when(service.registerHQ(any())).thenThrow(HQAlreadyRegistered.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/hq")
                        .content(objectMapper.writeValueAsString(mockHQDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].name", Matchers.is("Example HQ")))
                .andExpect(jsonPath("$.content[1].name", Matchers.is("Example HQ")))
                .andExpect(jsonPath("$.content[2].name", Matchers.is("Example HQ")));
    }

    @Test
    public void testFindByName_Success() throws Exception {
        when(service.findByName(anyString())).thenReturn(mockHQDto);


        mockMvc.perform(MockMvcRequestBuilders.get("/hq")
                        .param("name", "Example HQ"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockHQDto)));
    }

    @Test
    public void testFindByName_NotFound() throws Exception {
        when(service.findByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/hq")
                        .param("name", "Example HQ"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateHQ_Success() throws Exception {
        HQDTO updatedHQDto = HQTestUtil.createHQDTO();

        when(service.updateHQ(anyLong(), any(HQDTO.class))).thenReturn(updatedHQDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/hq/{id}", 1L)
                        .content(objectMapper.writeValueAsString(updatedHQDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedHQDto)));
    }


    @Test
    public void testUpdateHQ_NotFound() throws Exception {
        when(service.updateHQ(anyLong(), any(HQDTO.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/hq/{id}", 1L)
                        .content(objectMapper.writeValueAsString(HQTestUtil.createHQDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFilterHQsByPrice_Success() throws Exception {
        Page<HQDTO> mockPage = new PageImpl<>(Collections.emptyList());

        when(service.findHQsByPrice(anyDouble(), anyDouble(), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/hq/price")
                        .param("minPrice", "10.0")
                        .param("maxPrice", "50.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockPage)));
    }

    @Test
    public void testFilterHQsByPrice_Error() throws Exception {
        when(service.findHQsByPrice(anyDouble(), anyDouble(), any(Pageable.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/hq/price")
                        .param("minPrice", "10.0")
                        .param("maxPrice", "50.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFilterHQsByCategory_Success() throws Exception {
        List<HQDTO> mockHQList = new ArrayList<>();
        mockHQList.add(new HQDTO());
        mockHQList.add(new HQDTO());

        Page<HQDTO> mockPage = new PageImpl<>(mockHQList);

        when(service.findHQsByCategory(any(Category.class), any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/hq/categories")
                        .param("category", "OUTROS")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(mockHQList.size())));
    }

    @Test
    public void testFilterHQsByCategory_NoHQsFound() throws Exception {
        when(service.findHQsByCategory(any(Category.class), any(Pageable.class)))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/hq/categories")
                        .param("category", "OUTROS")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteHQ_Success() throws Exception {
        Long hqId = 1L;

        mockMvc.perform(delete("/hq/delete/{id}", hqId))
                .andExpect(status().isNoContent());

        verify(service).deleteHQ(hqId);
    }

    @Test
    public void testDeleteHQ_NotFound() throws Exception {
        Long nonExistentHQId = 10L;

        doThrow(new ResourceNotFoundException("HQ não encontrada")).when(service).deleteHQ(nonExistentHQId);

        mockMvc.perform(delete("/hq/delete/{id}", nonExistentHQId))
                .andExpect(status().isNotFound());
    }


}
