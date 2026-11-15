package io.dajzavrsi.dajzavrsi.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeUserPasswordRequestDTO(
    @NotBlank String username,
    @NotBlank String oldPassword,
    @NotBlank String newPassword) {}
