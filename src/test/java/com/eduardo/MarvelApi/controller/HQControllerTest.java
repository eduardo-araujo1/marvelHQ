package com.eduardo.MarvelApi.controller;

import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.exception.HQAlreadyRegistered;
import com.eduardo.MarvelApi.services.HQService;
import com.eduardo.MarvelApi.util.HQTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
}
