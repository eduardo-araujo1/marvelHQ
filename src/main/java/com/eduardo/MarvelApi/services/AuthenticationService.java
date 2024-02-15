package com.eduardo.MarvelApi.services;

import com.eduardo.MarvelApi.dto.AuthenticationDTO;
import com.eduardo.MarvelApi.dto.RegisterDTO;
import com.eduardo.MarvelApi.exception.AuthenticationFailureException;
import com.eduardo.MarvelApi.exception.UserAlreadyExistsException;
import com.eduardo.MarvelApi.infra.TokenService;
import com.eduardo.MarvelApi.model.User;
import com.eduardo.MarvelApi.enums.UserRole;
import com.eduardo.MarvelApi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public String login(AuthenticationDTO authenticationDTO) {
        UserDetails userDetails = loadUserByUsername(authenticationDTO.email());

        if (userDetails == null || !passwordEncoder.matches(authenticationDTO.password(), userDetails.getPassword())) {
            throw new AuthenticationFailureException("Email ou senha inválidos.");
        }
        return tokenService.generateToken((User) userDetails);
    }

    public void register(RegisterDTO registerDTO) {
        String email = registerDTO.email();
        if (userRepository.findByEmail(email) != null) {
            throw new UserAlreadyExistsException("O usuário com o e-mail fornecido já existe.");
        }
        String encryptedPassword = passwordEncoder.encode(registerDTO.password());
        User newUser = new User(registerDTO.name(), email, encryptedPassword, registerDTO.role());
        userRepository.save(newUser);
    }
}
