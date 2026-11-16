package io.dajzavrsi.dajzavrsi.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserResponseDTO(
    @NotNull Long id,
    @NotBlank String password) {}
