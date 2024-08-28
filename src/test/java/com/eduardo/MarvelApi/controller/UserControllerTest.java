package com.eduardo.MarvelApi.controller;

import com.eduardo.MarvelApi.dto.AuthenticationDTO;
import com.eduardo.MarvelApi.dto.RegisterDTO;
import com.eduardo.MarvelApi.enums.UserRole;
import com.eduardo.MarvelApi.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testLogin() throws Exception {
        String fakeToken = "fakeToken";
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("fakeEmail", "fakePassword");

        when(authenticationService.login(any(AuthenticationDTO.class))).thenReturn(fakeToken);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"fakeEmail\", \"password\":\"fakePassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(fakeToken));
    }

    @Test
    public void testRegister() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("fakeName", "fakeEmail", "fakePassword");

        doNothing().when(authenticationService).register(any(RegisterDTO.class));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"fakeName\", \"email\":\"fakeEmail\", \"password\":\"fakePassword\",}"))
                .andExpect(status().isOk());
    }
}
