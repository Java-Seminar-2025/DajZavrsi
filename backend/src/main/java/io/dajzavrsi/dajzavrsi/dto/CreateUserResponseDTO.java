package io.dajzavrsi.dajzavrsi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserResponseDTO(
    @NotNull Long id,
    @NotBlank String password) {}
