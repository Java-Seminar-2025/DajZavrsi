package io.dajzavrsi.dajzavrsi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.dajzavrsi.dajzavrsi.entity.Role;

public record CreateUserRequestDTO(
    @NotBlank String username,
    @NotNull Role role) {}
