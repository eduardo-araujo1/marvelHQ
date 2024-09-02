package com.eduardo.MarvelApi.controller;

import com.eduardo.MarvelApi.dto.AuthenticationDTO;
import com.eduardo.MarvelApi.dto.LoginResponseDTO;
import com.eduardo.MarvelApi.dto.RegisterDTO;
import com.eduardo.MarvelApi.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "auth", description = "API para autenticação e registro de usuários")
public class UserController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Realizar login", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso. Retorna o token de autenticação."),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas fornecidas.")
    })

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO authenticationDTO) {
        String token = authenticationService.login(authenticationDTO);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(summary = "Registrar um novo usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso."),
            @ApiResponse(responseCode = "409", description = "O usuário com o e-mail fornecido já existe.")
    })
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDTO registerDTO) {
        authenticationService.register(registerDTO);
        return ResponseEntity.ok().build();
    }
}
