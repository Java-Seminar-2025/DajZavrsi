package io.dajzavrsi.dajzavrsi.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.dajzavrsi.dajzavrsi.user.Role;

public record CreateUserRequestDTO(
    @NotBlank String username,
    @NotNull Role role) {}
