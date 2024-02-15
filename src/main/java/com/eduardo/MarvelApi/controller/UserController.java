package com.eduardo.MarvelApi.controller;

import com.eduardo.MarvelApi.dto.AuthenticationDTO;
import com.eduardo.MarvelApi.dto.LoginResponseDTO;
import com.eduardo.MarvelApi.dto.RegisterDTO;
import com.eduardo.MarvelApi.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {


    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO authenticationDTO) {
        String token = authenticationService.login(authenticationDTO);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        authenticationService.register(registerDTO);
        return ResponseEntity.ok().build();
    }
}
