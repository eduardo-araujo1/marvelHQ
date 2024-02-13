package com.eduardo.MarvelApi.dto;

import com.eduardo.MarvelApi.model.UserRole;

public record RegisterDTO(String name, String email, String password, UserRole role) {
}
