package com.eduardo.MarvelApi.dto;

import com.eduardo.MarvelApi.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotNull String name, @NotNull String email,@NotNull String password,@NotNull UserRole role) {
}
