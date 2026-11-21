package io.dajzavrsi.dajzavrsi.user.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserResponseDTO(
    @NotNull UUID id,
    @NotBlank String password) {}
