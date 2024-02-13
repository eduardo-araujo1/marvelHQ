package com.eduardo.MarvelApi.dto;

import com.eduardo.MarvelApi.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record AuthenticationDTO(String email, String password) {

}
