package com.eduardo.MarvelApi.service;

import com.eduardo.MarvelApi.dto.AuthenticationDTO;
import com.eduardo.MarvelApi.dto.RegisterDTO;
import com.eduardo.MarvelApi.enums.UserRole;
import com.eduardo.MarvelApi.exception.AuthenticationFailureException;
import com.eduardo.MarvelApi.exception.UserAlreadyExistsException;
import com.eduardo.MarvelApi.infra.TokenService;
import com.eduardo.MarvelApi.model.User;
import com.eduardo.MarvelApi.repositories.UserRepository;
import com.eduardo.MarvelApi.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testLogin_SuccessfulAuthentication() {
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        User user = new User(1L,"Eduardo","test@example.com","password", UserRole.ADMIN);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(tokenService.generateToken(any(User.class))).thenReturn("generatedToken");


        String token = authenticationService.login(new AuthenticationDTO(email, password));

        assertNotNull(token);
        verify(tokenService).generateToken(user);
    }

    @Test
    public void testLogin_InvalidCredentials() {
        String email = "test@example.com";
        String password = "password";
        User user = null;

        when(userRepository.findByEmail(email)).thenReturn(user);

        assertThrows(AuthenticationFailureException.class, () -> {
            authenticationService.login(new AuthenticationDTO(email, password));
        });
    }

    @Test
    public void testRegister_NewUser() {
        RegisterDTO registerDTO = new RegisterDTO("Teste", "teste@example.com", "password", UserRole.USER);
        when(userRepository.findByEmail(registerDTO.email())).thenReturn(null);
        when(passwordEncoder.encode(registerDTO.password())).thenReturn("encodedPassword");

        authenticationService.register(registerDTO);

        verify(userRepository).save(argThat(user ->
                user.getName().equals(registerDTO.name()) &&
                        user.getEmail().equals(registerDTO.email()) &&
                        user.getPassword().equals("encodedPassword") &&
                        user.getRole().equals(registerDTO.role())
        ));
    }

    @Test
    public void testRegister_UserAlreadyExists() {
        RegisterDTO registerDTO = new RegisterDTO("Teste", "teste@example.com", "password", UserRole.USER);

        when(userRepository.findByEmail(registerDTO.email())).thenReturn(new User());

        assertThrows(UserAlreadyExistsException.class, () -> {
            authenticationService.register(registerDTO);
        });

        verify(userRepository, never()).save(any(User.class));
    }

}
